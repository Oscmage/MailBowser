package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.email.views.EmailListViewItem;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by filip on 04/05/15.
 */

public class EmailListPresenter implements Initializable, IObserver {

    // OK, do not get frightened. Read it like so: "An email-list ListView."
    @FXML protected ListView<EmailListViewItem> emailListListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.INSTANCE.register(this);
    }

    private void updateListView(List<IEmail> emails) {

        ObservableList<EmailListViewItem> emailListItems = FXCollections.observableArrayList();

        for(IEmail email : emails) {
            EmailListViewItem emailListViewItem = new EmailListViewItem(email);
            emailListItems.add(emailListViewItem);
        }

        emailListListView.setItems(emailListItems);
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

    @Override
    public void onEvent(IEvent evt) {
        switch (evt.getType()) {
            case FETCH_EMAILS:
                List<IEmail> emails = (List<IEmail>)evt.getValue();
                updateListView(emails);
        }
    }

}