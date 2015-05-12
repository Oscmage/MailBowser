package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.email.models.Address;
import edu.chl.mailbowser.email.models.Email;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.email.views.EmailListViewItem;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import javafx.scene.layout.Pane;
import javafx.util.Callback;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import java.net.URL;
import java.util.*;
import java.util.List;

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

    /**
     * Updates the EmailDetailView, and sets its content to whatever the current email is.
     * @param emails
     */
    private void updateListView(List<IEmail> emails) {
        for(IEmail email : emails) {
            updateListView(email);
        }
    }

    private void updateListView(IEmail email) {
        ObservableList<EmailListViewItem> observableList = emailListListView.getItems();

        EmailListViewItem emailListViewItem = new EmailListViewItem((Email)email);

        if(!observableList.contains(emailListViewItem)) {
            if(observableList.size()!=0) {
                for (int i = 0 ; i < observableList.size() ; i++) {
                    if (emailListViewItem.compareTo(observableList.get(i)) < 0) {
                        observableList.add(i, emailListViewItem);
                        break;
                    }
                    if(i == observableList.size()-1){
                        observableList.add(emailListViewItem);
                        break;
                    }
                }
            }else {
                observableList.add(emailListViewItem);
            }
            emailListListView.setItems(observableList);
        }
    }

    private class SortCompator<T> implements Comparator{

        @Override
        public int compare(Object o1, Object o2) {
            if(o1 instanceof Comparable || o2 instanceof Comparable){
                return ((Comparable)o1).compareTo((Comparable)o2);
            }
            throw new IllegalArgumentException();
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

    @Override
    public void onEvent(IEvent evt) {
        switch (evt.getType()) {
            case FETCH_EMAIL:
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        updateListView((IEmail)evt.getValue());
                    }
                });
            case FETCH_EMAILS:
                //updateListView((List<IEmail>)evt.getValue());
        }
    }

}