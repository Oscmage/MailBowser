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
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;


import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by filip on 04/05/15.
 */
public class EmailListPresenter implements Initializable, IObserver {

    private IAccountHandler accountHandler = MainHandler.INSTANCE.getAccountHandler();
    private ITagHandler tagHandler = MainHandler.INSTANCE.getTagHandler();

    // this list holds all EmailListViewItems. when you add an item to this list, emailListListView will update
    // itself automatically
    @FXML private ObservableList<EmailListItemPresenter> observableEmailList = FXCollections.observableArrayList();

    // a wrapper for observableEmailList that automatically sorts the items using the compareTo method in EmailListItemPresenter
    @FXML private SortedList<EmailListItemPresenter> sortedObservableEmailList = new SortedList<>(observableEmailList,
            Comparator.<EmailListItemPresenter>naturalOrder());

    // OK, do not get frightened. Read it like so: "An email-list ListView."
    @FXML protected ListView<EmailListItemPresenter> emailListListView;

    // this flag determines whether or not to update the list view when a FETCHED_EMAIL event comes in. it is set to
    // false when you search
    private boolean updateListOnIncomingEmail = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.INSTANCE.register(this);

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
    private void replaceListViewContent(List<IEmail> emails) {
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
        List<IEmail> emails = accountHandler.getAllEmails();

        if (!query.equals("")) {
            updateListOnIncomingEmail = false;
            List<IEmail> matchingEmails = Searcher.search(emails, query);
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
                replaceListViewContent(new ArrayList<>(tagHandler.getEmailsWith(pair.getSecond())));
                break;
            case CLEAR_EMAILS:
                clearEmails();
                break;
            case SELECT_TAG:
                if(evt.getValue() != null) {
                    replaceListViewContent(new ArrayList<>(tagHandler.getEmailsWith((ITag) evt.getValue())));
                } else {
                    replaceListViewContent(new ArrayList<>(accountHandler.getAllEmails()));
                }
                break;
        }
    }
}