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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by filip on 04/05/15.
 */

public class EmailListPresenter implements Initializable, IObserver {
    private Map<Pane,IEmail> paneIEmailMap;

    // OK, do not get frightened. Read it like so: "An email-list ListView."
    @FXML protected ListView<Pane> emailListListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.INSTANCE.register(this);
    }

    private void updateListView(List<IEmail> emails) {

        this.paneIEmailMap = new HashMap<>(); // Created for keeping track on which email belongs to what pane.

        ObservableList<Pane> emailListItems = FXCollections.observableArrayList();

        for(IEmail email : emails) {

            EmailListViewItem emailListViewItem = new EmailListViewItem(email);

            emailListItems.add(emailListViewItem);
            this.paneIEmailMap.put(emailListViewItem,email);
        }

        emailListListView.setItems(emailListItems);
    }


    /**
     * Sends an event when a different email is selected.
     * @param evt
     */
    public void onItemChanged(Event evt) {
        IEmail email = paneIEmailMap.get(this.emailListListView.getSelectionModel().getSelectedItem());
        EventBus.INSTANCE.publish(new edu.chl.mailbowser.event.Event(EventType.SELECTED_EMAIL,email));
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