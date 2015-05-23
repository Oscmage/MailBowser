package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.account.handlers.IAccountHandler;
import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TopbarPresenter implements Initializable, IObserver{

    private IEmail email;
    private IAccountHandler accountHandler = MainHandler.INSTANCE.getAccountHandler();

    @FXML protected TextField addTagTextField;
    @FXML protected TextField searchField;

    @FXML protected HBox actionButtons;
    @FXML protected Button forwardButton;
    @FXML protected Button replyButton;
    @FXML protected Button replyAllButton;

    @FXML protected Button fetchButton;
    @FXML protected Button addTagButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.INSTANCE.register(this);
        showOrHideButtons();
    }

    private void showOrHideButtons() {
        List<Node> buttons = actionButtons.getChildren().stream()
                .filter(t -> t.getClass() == Button.class)
                .map(o -> (Button) o)
                .collect(Collectors.toList());

        if(email == null) {
            buttons.stream()
                    .forEach(s -> s.setDisable(true));
        } else {
            buttons.stream()
                    .forEach(s -> s.setDisable(false));
        }
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
        EventBus.INSTANCE.publish(new Event(EventType.REPLY_ALL, root));
    }

    @Override
    public void onEvent(IEvent event) {
        Platform.runLater( // JavaFX can get thread problems otherwise
                () -> handleEvent(event)
        );
    }

    private void handleEvent(IEvent event){
        switch (event.getType()) {
            case SELECTED_EMAIL:
                this.email = (IEmail) event.getValue();
                break;
        }
        showOrHideButtons();
    }

}




