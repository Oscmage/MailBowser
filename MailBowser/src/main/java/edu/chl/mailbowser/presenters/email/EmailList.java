package edu.chl.mailbowser.presenters.email;

import edu.chl.mailbowser.main.MainHandler;
import edu.chl.mailbowser.account.IAccountHandler;
import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.IObserver;
import edu.chl.mailbowser.utils.search.SetSearcher;
import edu.chl.mailbowser.tag.ITagHandler;
import edu.chl.mailbowser.tag.ITag;
import edu.chl.mailbowser.utils.Pair;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import java.io.IOException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Created by filip on 04/05/15.
 */
public class EmailList extends ListView implements IObserver {

    private IAccountHandler accountHandler = MainHandler.INSTANCE.getAccountHandler();
    private ITagHandler tagHandler = MainHandler.INSTANCE.getTagHandler();

    @FXML private ObservableList<EmailListItem> observableEmailList = FXCollections.observableArrayList();
    @FXML private SortedList<EmailListItem> sortedObservableEmailList = new SortedList<>(observableEmailList,
            Comparator.<EmailListItem>naturalOrder());
    @FXML protected ListView<EmailListItem> emailListListView;
    private boolean updateListOnIncomingEmail = true;

    private ITag selectedTag = null;

    public EmailList() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/email/EmailList.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e.getCause());
        }

        EventBus.INSTANCE.register(this);
        initializeList();
    }

    /**
     * Adds all emails, from all accounts, to the list, selects the first and assigns a listener to "select"-events.
     */
    private void initializeList() {
        emailListListView.setItems(sortedObservableEmailList);

        emailListListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                EventBus.INSTANCE.publish(new Event(EventType.SELECT_EMAIL, newValue.getEmail()));
            }
        });

        replaceListViewContent(accountHandler.getAllEmails());

        if(observableEmailList.size() != 0) {
            this.emailListListView.getSelectionModel().selectFirst();
        }
    }


    /**
     * Replaces all items in emailListListView with new list items for the given emails
     *
     * @param emails the new emails to show in the list
     */
    private void replaceListViewContent(Set<IEmail> emails) {
        observableEmailList.setAll(emails.stream()
                        .map(EmailListItem::new)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Adds a single email to emailListListView
     *
     * @param email
     */
    private void addEmailToListView(IEmail email) {
        EmailListItem emailListItem = new EmailListItem(email);
        if (!observableEmailList.contains(emailListItem)) {
            observableEmailList.add(emailListItem);
        }
    }

    /**
     * Removes a single email from the list.
     *
     * @param email the email to remove
     */
    private void removeEmailFromList(IEmail email) {
        EmailListItem emailListItem = new EmailListItem(email);
        this.observableEmailList.remove(emailListItem);
    }

    /**
     * This method is called when a search event comes in. It filters all emails and calls replaceListViewContent
     * with the result.
     *
     * @param query the query to search for
     */
    private void searchOccured(String query) {
        if (!query.equals("")) {
            updateListOnIncomingEmail = false;

            Set<IEmail> emails = accountHandler.getAllEmails();
            Set<IEmail> matchingEmails = new SetSearcher<IEmail>().search(emails, query);
            replaceListViewContent(matchingEmails);
        } else {
            updateListOnIncomingEmail = true;

            if (selectedTag == null) {
                replaceListViewContent(accountHandler.getAllEmails());
            } else {
                replaceListViewContent(tagHandler.getEmailsWithTag(selectedTag));
            }
        }
    }

    /**
     * This method is called when a fetch event comes in.
     *
     * @param email the fetched email
     */
    private void fetchedEmail(IEmail email) {
        if (selectedTag == null && updateListOnIncomingEmail) {
            addEmailToListView(email);
        }
    }

    /**
     * This method is called when a clear emails event comes in.
     */
    private void clearEmails() {
        observableEmailList.clear();
    }

    /**
     * This method is called when an email gets tagged
     * @param pair
     */
    private void addedTagToEmail(Pair<IEmail, ITag> pair) {
        if (pair.getSecond().equals(selectedTag) && updateListOnIncomingEmail) {
            addEmailToListView(pair.getFirst());
        }
    }

    private void removedTagFromEmail(Pair<IEmail, ITag> pair) {
        if (pair.getSecond().equals(selectedTag) && updateListOnIncomingEmail) {
            removeEmailFromList(pair.getFirst());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(IEvent evt) {
        Platform.runLater( // JavaFX can get thread problems otherwise
                () -> handleEvent(evt)
        );
    }

    private void handleEvent(IEvent evt){
        switch (evt.getType()) {
            case FETCHED_EMAIL:
                fetchedEmail((IEmail) evt.getValue());
                break;
            case SEARCH:
                searchOccured((String) evt.getValue());
                break;
            case ADDED_TAG_TO_EMAIL:
                addedTagToEmail((Pair<IEmail, ITag>) evt.getValue());
                break;
            case REMOVED_TAG_FROM_EMAIL:
                removedTagFromEmail((Pair<IEmail, ITag>)evt.getValue());
                break;
            case CLEAR_EMAILS:
                clearEmails();
                break;
            case SELECT_TAG:
                this.selectedTag = (ITag) evt.getValue();
                if(evt.getValue() != null) {
                    replaceListViewContent(tagHandler.getEmailsWithTag(selectedTag));
                } else {
                    replaceListViewContent(accountHandler.getAllEmails());
                }
                break;
        }
    }
}