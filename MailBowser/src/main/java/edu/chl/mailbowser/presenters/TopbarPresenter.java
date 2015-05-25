package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.account.handlers.IAccountHandler;
import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.*;
import edu.chl.mailbowser.tag.handlers.ITagHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TopbarPresenter implements Initializable, IObserver{

    private IEmail email;
    private IAccountHandler accountHandler = MainHandler.INSTANCE.getAccountHandler();
    private ITagHandler tagHandler = MainHandler.INSTANCE.getTagHandler();

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
        Stage root = (Stage) ((Node) actionEvent.getTarget()).getScene().getWindow();
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
        EventBus.INSTANCE.publish(new Event(EventType.DELETE_EMAIL, email));
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
                this.email = (IEmail) event.getValue();
                break;
        }
        showOrHideButtons();
    }

    /**
     * This method is invoked when the openContactBookButton is pressed.
     *
     * @param actionEvent
     */
    public void openContactBookButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage newStage = new Stage();
        Parent node = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ContactBookView.fxml"));
        newStage.setTitle("Contact Book");

        Scene scene = new Scene(node, 400, 300);
        scene.getStylesheets().add("css/style.css");

        newStage.setMinWidth(400.0);
        newStage.setMinHeight(300.0);

        newStage.setScene(scene);
        newStage.show();
    }
}




