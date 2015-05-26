package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.main.MainHandler;
import edu.chl.mailbowser.account.IAccountHandler;
import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.IObserver;
import edu.chl.mailbowser.search.Searcher;
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
public class EmailListPresenter extends ListView implements IObserver {

    private IAccountHandler accountHandler = MainHandler.INSTANCE.getAccountHandler();
    private ITagHandler tagHandler = MainHandler.INSTANCE.getTagHandler();

    @FXML private ObservableList<EmailListItemPresenter> observableEmailList = FXCollections.observableArrayList();
    @FXML private SortedList<EmailListItemPresenter> sortedObservableEmailList = new SortedList<>(observableEmailList,
            Comparator.<EmailListItemPresenter>naturalOrder());
    @FXML protected ListView<EmailListItemPresenter> emailListListView;
    private boolean updateListOnIncomingEmail = true;

    public EmailListPresenter() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EmailListView.fxml"));
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

        replaceListViewContent(accountHandler.getAllEmails());

        if(observableEmailList.size() != 0) {
            this.emailListListView.getSelectionModel().selectFirst();
            EventBus.INSTANCE.publish(new Event(EventType.SELECT_EMAIL,
                    this.emailListListView.getSelectionModel().getSelectedItem().getEmail()));
        }

        emailListListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                EventBus.INSTANCE.publish(new Event(EventType.SELECT_EMAIL, newValue.getEmail()));
            }
        });
    }


    /**
     * Replaces all items in emailListListView with new list items for the given emails
     *
     * @param emails the new emails to show in the list
     */
    private void replaceListViewContent(Set<IEmail> emails) {
        observableEmailList.setAll(emails.stream()
                        .map(EmailListItemPresenter::new)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Adds a single email to emailListListView
     *
     * @param email
     */
    private void addEmailToListView(IEmail email) {
        EmailListItemPresenter emailListItemPresenter = new EmailListItemPresenter(email);
        if (!observableEmailList.contains(emailListItemPresenter)) {
            observableEmailList.addAll(emailListItemPresenter);
        }
    }

    /**
     * This method is called when a search event comes in. It filters all emails and calls replaceListViewContent
     * with the result.
     *
     * @param query the query to search for
     */
    private void search(String query) {
        Set<IEmail> emails = accountHandler.getAllEmails();

        if (!query.equals("")) {
            updateListOnIncomingEmail = false;
            Set<IEmail> matchingEmails = Searcher.search(emails, query);
            System.out.println("matching emails: " + matchingEmails.size());
            replaceListViewContent(matchingEmails);
        } else {
            updateListOnIncomingEmail = true;
            replaceListViewContent(emails);
        }
    }

    /**
     * This method is called when a fetch event comes in.
     *
     * @param email the fetched email
     */
    private void fetchEmail(IEmail email) {
        if (updateListOnIncomingEmail) {
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
                fetchEmail((IEmail) evt.getValue());
                break;
            case SEARCH:
                search((String) evt.getValue());
                break;
            case REMOVED_TAG_FROM_EMAIL:
                Pair<IEmail, ITag> pair = (Pair<IEmail, ITag>)evt.getValue();
                replaceListViewContent(new TreeSet<>(tagHandler.getEmailsWith(pair.getSecond())));
                break;
            case CLEAR_EMAILS:
                clearEmails();
                break;
            case SELECT_TAG:
                if(evt.getValue() != null) {
                    replaceListViewContent(new TreeSet<>(tagHandler.getEmailsWith((ITag) evt.getValue())));
                } else {
                    replaceListViewContent(new TreeSet<>(accountHandler.getAllEmails()));
                }
                break;
        }
    }
}