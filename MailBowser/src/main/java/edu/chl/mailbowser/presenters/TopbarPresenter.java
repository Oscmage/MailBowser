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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

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
        disableButtons(true);
    }

    private void disableButtons(boolean disableButtons) {
        List<Node> buttons = actionButtons.getChildren().stream()
                .filter(t -> t.getClass() == Button.class)
                .map(o -> (Button) o)
                .collect(Collectors.toList());

        if(disableButtons) {
            buttons.stream()
                    .forEach(s -> s.setDisable(true));
        } else {
            buttons.stream()
                    .forEach(s -> s.setDisable(false));
        }
    }

    @FXML
    public void tagButtonOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.OPEN_ADD_TAG_WINDOW, null));
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
        EventBus.INSTANCE.publish(new Event(EventType.OPEN_COMPOSE_EMAIL_WINDOW, null));
    }

    @FXML
    private void forwardButtonOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.OPEN_COMPOSE_EMAIL_WINDOW_FORWARD, email));
    }

    @FXML
    private void replyButtonOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.OPEN_COMPOSE_EMAIL_WINDOW_REPLY, email));
    }

    @FXML
    private void replyAllButtonOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.OPEN_COMPOSE_EMAIL_WINDOW_REPLY_ALL, email));
    }

    @FXML
    private void deleteButtonOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.MARK_EMAIL_AS_DELETED, email));
    }

    @FXML
    public void openContactBookButtonOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.OPEN_CONTACT_BOOK, email));
    }

    @Override
    public void onEvent(IEvent event) {
        Platform.runLater( // JavaFX can get thread problems otherwise
                () -> handleEvent(event)
        );
    }

    private void handleEvent(IEvent event){
        switch (event.getType()) {
            case SELECT_EMAIL:
                if(event.getValue() != null) {
                    disableButtons(false);
                } else {
                    disableButtons(true);
                }
                break;
        }

    }

}




