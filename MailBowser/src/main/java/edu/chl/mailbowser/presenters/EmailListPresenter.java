package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.account.handlers.AccountHandler;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.email.views.EmailListViewItem;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
import edu.chl.mailbowser.search.Searcher;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Created by filip on 04/05/15.
 */

public class EmailListPresenter implements Initializable, IObserver {

    // OK, do not get frightened. Read it like so: "An email-list ListView."
    @FXML protected ListView<EmailListViewItem> emailListListView;

    private boolean searchActivated = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.INSTANCE.register(this);
    }

    /**
     * Updates the EmailDetailView, and sets its content to whatever the current email is.
     * @param emails
     */
    private void updateListView(List<IEmail> emails) {

        List<EmailListViewItem> list = new ArrayList<>();

        ObservableList<EmailListViewItem> observableList = FXCollections.observableList(list);

        for(IEmail email : emails) {

            EmailListViewItem emailListViewItem = new EmailListViewItem(email);

            observableList.add(emailListViewItem);

        }

        emailListListView.setItems(observableList);
    }

    private void updateListView(IEmail email) {
        ObservableList<EmailListViewItem> observableList = emailListListView.getItems();

        if(!observableList.contains(email)) {
            EmailListViewItem emailListViewItem = new EmailListViewItem(email);

            observableList.add(emailListViewItem);

            emailListListView.setItems(observableList);
        }

    }

    private void search(String query) {
        List<IEmail> emails = AccountHandler.getInstance().getAccount().getEmails();

        if (query != "") {
            searchActivated = true;
            List<IEmail> matchingEmails = Searcher.search(emails, query);
            updateListView(matchingEmails);
        } else {
            searchActivated = false;
            updateListView(emails);
        }
    }

    private void fetchEmail(IEmail email) {
        if (!searchActivated) {
            updateListView(email);
        }
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
        switch (evt.getType()) {
            case FETCH_EMAIL:
                Platform.runLater(
                        () -> fetchEmail((IEmail) evt.getValue())
                );
            case FETCH_EMAILS:
                //updateListView((List<IEmail>)evt.getValue())
                break;
            case SEARCH:
                Platform.runLater(
                        () -> search((String) evt.getValue())
                );
                break;
        }
    }

}