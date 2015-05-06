package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.account.handlers.AccountHandler;
import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by filip on 04/05/15.
 */
public class EmailListPresenter implements Initializable, IObserver {

    // OK, do not get frightened. Read it like so: "An email-list ListView."
    @FXML
    private ListView<Pane> emailListListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.INSTANCE.register(this);
    }

    private void updateListView(List<IEmail> emails) {

        ObservableList<Pane> emailListItems = FXCollections.observableArrayList();

        for(IEmail email : emails) {
            Pane emailListItem = new AnchorPane();
            VBox emailListItemContent = new VBox();

            emailListItem.setPrefWidth(240.0);
            emailListItemContent.setPrefWidth(240.0);

            Label emailSubject = new Label(email.getSubject());

            emailListItemContent.getChildren().addAll(
                    emailSubject
            );

            emailListItem.getChildren().addAll(emailListItemContent);
            emailListItems.add(emailListItem);
        }

        emailListListView.setItems(emailListItems);
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