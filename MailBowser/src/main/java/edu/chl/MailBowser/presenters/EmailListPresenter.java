package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.account.handlers.AccountHandler;
import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by filip on 04/05/15.
 */
public class EmailListPresenter implements Initializable {
    private Map<Pane,IEmail> paneIEmailMap;

    // OK, do not get frightened. Read it like so: "An email-list ListView."
    @FXML
    protected ListView<Pane> emailListListView;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.paneIEmailMap = new HashMap<>(); // Created for keeping track on which email belongs to what pane.
        IAccount account = AccountHandler.INSTANCE.getAccount(0);
        List<IEmail> emails = AccountHandler.INSTANCE.getAccount(0).getIncomingServer().fetch(account.getUsername(), account.getPassword());

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

            this.paneIEmailMap.put(emailListItem,email);
        }

        emailListListView.setItems(emailListItems);

    }


    public void selectedItemChanged(Event evt) {
        IEmail email = paneIEmailMap.get(this.emailListListView.getSelectionModel().getSelectedItem());
        EventBus.INSTANCE.publish(new edu.chl.mailbowser.event.Event(EventType.SELECTED_EMAIL,email));
    }

}