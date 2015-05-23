package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.account.handlers.IAccountHandler;
import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TopbarPresenter implements Initializable {

    private IEmail email;
    private IAccountHandler accountHandler = MainHandler.INSTANCE.getAccountHandler();

    @FXML protected TextField addTagTextField;
    @FXML protected TextField searchField;
    @FXML protected Button fetchButton;
    @FXML protected Button addTagButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }



    @FXML
    public void tagButtonOnAction(ActionEvent actionEvent) {
        Stage root = (Stage) ((Node) actionEvent.getTarget()).getScene().getWindow();
        AddTagPresenter addTagPresenter = new AddTagPresenter(email);

        Stage addTagStage = new Stage();
        addTagStage.setTitle("Add tag...");

        addTagStage.initModality(Modality.APPLICATION_MODAL);
        addTagStage.initOwner(root);

        addTagStage.setScene(new Scene(addTagPresenter, 300, 200));
        addTagStage.show();
    }

    @FXML
    private void searchFieldOnAction(ActionEvent actionEvent) {
        String text = searchField.getText();
        EventBus.INSTANCE.publish(new Event(EventType.SEARCH, text));
    }

    @FXML
    private void fetchButtonOnAction(ActionEvent actionEvent) {
        for(IAccount account : accountHandler.getAccounts()) {
            account.fetch();
        }
    }

    @FXML
    private void newButtonOnAction(ActionEvent actionEvent) {
        Stage root = (Stage) ((Node) actionEvent.getTarget()).getScene().getWindow();
        EventBus.INSTANCE.publish(new Event(EventType.NEW_EMAIL,root));
    }

    @FXML
    private void forwardButtonOnAction(ActionEvent actionEvent) {
        Stage root = (Stage) ((Node) actionEvent.getTarget()).getScene().getWindow();
        EventBus.INSTANCE.publish(new Event(EventType.FORWARD,root));
    }

    @FXML
    private void replyButtonOnAction(ActionEvent actionEvent) {
        Stage root = (Stage) ((Node) actionEvent.getTarget()).getScene().getWindow();
        EventBus.INSTANCE.publish(new Event(EventType.REPLY,root));
    }

    @FXML
    private void replyAllButtonOnAction(ActionEvent actionEvent) {
        Stage root = (Stage) ((Node) actionEvent.getTarget()).getScene().getWindow();
        EventBus.INSTANCE.publish(new Event(EventType.REPLY_ALL,root));
    }

}




