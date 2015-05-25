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
    protected void searchFieldOnAction(ActionEvent actionEvent) {
        String text = searchField.getText();
        EventBus.INSTANCE.publish(new Event(EventType.SEARCH, text));
    }

    @FXML
    protected void fetchButtonOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.FETCH_ALL_EMAILS, null));
    }

    @FXML
    protected void newButtonOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.OPEN_COMPOSE_EMAIL_WINDOW, null));
    }

    @FXML
    protected void forwardButtonOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.OPEN_COMPOSE_EMAIL_WINDOW_FORWARD, null));
    }

    @FXML
    protected void replyButtonOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.OPEN_COMPOSE_EMAIL_WINDOW_REPLY, null));
    }

    @FXML
    protected void replyAllButtonOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.OPEN_COMPOSE_EMAIL_WINDOW_REPLY_ALL, null));
    }

    @FXML
    protected void deleteButtonOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.MARK_EMAIL_AS_DELETED, null));
    }

    @FXML
    protected void openContactBookButtonOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.OPEN_CONTACT_BOOK, null));
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




