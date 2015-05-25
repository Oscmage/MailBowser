package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.account.IAccountHandler;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.email.views.EmailListViewItem;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
import edu.chl.mailbowser.search.Searcher;
import edu.chl.mailbowser.tag.handlers.ITagHandler;
import edu.chl.mailbowser.tag.models.ITag;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by filip on 04/05/15.
 */
public class EmailListPresenter implements Initializable, IObserver, ActionListener {

    private javax.swing.Timer t = new Timer(5000,this);
    private IAccountHandler accountHandler = MainHandler.INSTANCE.getAccountHandler();
    private ITagHandler tagHandler = MainHandler.INSTANCE.getTagHandler();

    // this list holds all EmailListViewItems. when you add an item to this list, emailListListView will update
    // itself automatically
    @FXML private ObservableList<EmailListViewItem> observableEmailList = FXCollections.observableArrayList();

    // a wrapper for observableEmailList that automatically sorts the items using the compareTo method in EmailListViewItem
    @FXML private SortedList<EmailListViewItem> sortedObservableEmailList = new SortedList<>(observableEmailList,
            Comparator.<EmailListViewItem>naturalOrder());

    // OK, do not get frightened. Read it like so: "An email-list ListView."
    @FXML protected ListView<EmailListViewItem> emailListListView;

    // this flag determines whether or not to update the list view when a FETCH_EMAIL event comes in. it is set to
    // false when you search
    private boolean updateListOnIncomingEmail = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.INSTANCE.register(this);

        emailListListView.setItems(sortedObservableEmailList);

        replaceListViewContent(accountHandler.getAllEmails());

        this.emailListListView.getSelectionModel().selectFirst();

    }


    /**
     * Replaces all items in emailListListView with new list items for the given emails
     *
     * @param emails the new emails to show in the list
     */
    private void replaceListViewContent(List<IEmail> emails) {
        observableEmailList.setAll(emails.stream()
                        .map(EmailListViewItem::new)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Adds a single email to emailListListView
     *
     * @param email
     */
    private void addEmailToListView(IEmail email) {
        EmailListViewItem emailListViewItem = new EmailListViewItem(email);
        if (!observableEmailList.contains(emailListViewItem)) {
            observableEmailList.addAll(emailListViewItem);
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
     * Sends an event when a different email is selected.
     * @param evt
     */
    public void onItemChanged(Event evt) {
        EventBus.INSTANCE.publish(new edu.chl.mailbowser.event.Event(
                EventType.SELECTED_EMAIL, this.emailListListView.getSelectionModel().getSelectedItem().getEmail()
        ));
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
            case EMAILDETAILPRESENTER_READY:
                IEmail email = this.emailListListView.getSelectionModel().getSelectedItem().getEmail();
                EventBus.INSTANCE.publish(new edu.chl.mailbowser.event.Event(EventType.SELECTED_EMAIL,email));
                break;
            case FETCH_EMAIL:
                fetchEmail((IEmail) evt.getValue());
                break;
            case SEARCH:
                search((String) evt.getValue());
                break;
            case CLEAR_EMAILS:
                clearEmails();
                break;
            case SELECTED_TAG:
                if(evt.getValue() != null) {
                    replaceListViewContent(new ArrayList<>(tagHandler.getEmailsWith((ITag) evt.getValue())));
                } else {
                    replaceListViewContent(new ArrayList<>(accountHandler.getAllEmails()));
                }
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EventBus.INSTANCE.publish(new edu.chl.mailbowser.event.Event(EventType.SELECTED_EMAIL,
                this.emailListListView.getSelectionModel().getSelectedItem().getEmail()));
        t.stop();
    }
}