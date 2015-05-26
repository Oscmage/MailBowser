package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.event.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TopbarPresenter extends HBox implements IObserver{

    @FXML protected TextField addTagTextField;
    @FXML protected TextField searchField;
    @FXML protected HBox actionButtons;
    @FXML protected Button forwardButton;
    @FXML protected Button replyButton;
    @FXML protected Button replyAllButton;
    @FXML protected Button fetchButton;
    @FXML protected Button addTagButton;

    public TopbarPresenter() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TopbarView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e.getCause());
        }

        EventBus.INSTANCE.register(this);
        toggleDisableButtons(true);
    }

    /**
     * Disables the buttons to the right in the menu bar if no email is selected.
     * @param disableButtons
     */
    private void toggleDisableButtons(boolean disableButtons) {
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

    /**
     * Invoked when the "Tag"-button is clicked, and bubbles the event upwards.
     * @param actionEvent
     */
    @FXML
    public void tagButtonOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.OPEN_ADD_TAG_WINDOW, null));
    }

    /**
     * Invoked when the enter button is pressed while focusing the search field, and bubbles the event upwards.
     * @param actionEvent
     */
    @FXML
    protected void searchFieldOnAction(ActionEvent actionEvent) {
        String text = searchField.getText();
        EventBus.INSTANCE.publish(new Event(EventType.SEARCH, text));
    }

    /**
     * Invoked when the "Fetch"-button is clicked, and bubbles the event upwards.
     * @param actionEvent
     */
    @FXML
    protected void fetchButtonOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.FETCH_ALL_EMAILS, null));
    }

    /**
     * Invoked when the "New Email"-button is clicked, and bubbles the event upwards.
     * @param actionEvent
     */
    @FXML
    protected void newButtonOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.OPEN_COMPOSE_EMAIL_WINDOW, null));
    }

    /**
     * Invoked when the "Forward Email"-button is clicked, and bubbles the event upwards.
     * @param actionEvent
     */
    @FXML
    protected void forwardButtonOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.OPEN_COMPOSE_EMAIL_WINDOW_FORWARD, null));
    }

    /**
     * Invoked when the "Reply Email"-button is clicked, and bubbles the event upwards.
     * @param actionEvent
     */
    @FXML
    protected void replyButtonOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.OPEN_COMPOSE_EMAIL_WINDOW_REPLY, null));
    }

    /**
     * Invoked when the "Reply All"-button is clicked, and bubbles the event upwards.
     * @param actionEvent
     */
    @FXML
    protected void replyAllButtonOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.OPEN_COMPOSE_EMAIL_WINDOW_REPLY_ALL, null));
    }

    /**
     * Invoked when the "Delete Email"-button is clicked, and bubbles the event upwards.
     * @param actionEvent
     */
    @FXML
    protected void deleteButtonOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.MARK_EMAIL_AS_DELETED, null));
    }

    /**
     * Invoked when the "Open Contact Book"-button is clicked, and bubbles the event upwards.
     * @param actionEvent
     */
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
                    toggleDisableButtons(false);
                } else {
                    toggleDisableButtons(true);
                }
                break;
        }

    }

}




