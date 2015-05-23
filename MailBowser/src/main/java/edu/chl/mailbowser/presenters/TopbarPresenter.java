package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.account.handlers.IAccountHandler;
import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.email.models.IAddress;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.*;
import edu.chl.mailbowser.tag.handlers.ITagHandler;
import edu.chl.mailbowser.tag.models.ITag;
import edu.chl.mailbowser.tag.models.Tag;
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

public class TopbarPresenter implements IObserver, Initializable {

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

    private void openComposeEmailWindow(Stage root, String recipient, String subject, String content) {
        ComposeEmailPresenter composeEmailPresenter = new ComposeEmailPresenter(recipient, subject, content);

        // create a new stage
        Stage newEmailStage = new Stage();
        newEmailStage.setTitle("New Email...");
        newEmailStage.setX(root.getX()+50);
        newEmailStage.setY(root.getY()+50);

        // add the component to the stage
        newEmailStage.setScene(new Scene(composeEmailPresenter));
        newEmailStage.show();
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
    public void searchFieldOnAction(ActionEvent actionEvent) {
        String text = searchField.getText();
        EventBus.INSTANCE.publish(new Event(EventType.SEARCH, text));
    }

    @FXML
    public void fetchButtonOnAction(ActionEvent actionEvent) {
        for(IAccount account : accountHandler.getAccounts()) {
            account.fetch();
        }
    }

    @FXML
    public void newButtonOnAction(ActionEvent actionEvent) {
        Stage root = (Stage) ((Node) actionEvent.getTarget()).getScene().getWindow();
        openComposeEmailWindow(root, "", "", "");
    }

    @FXML
    public void forwardButtonOnAction(ActionEvent actionEvent) {
        Stage root = (Stage) ((Node) actionEvent.getTarget()).getScene().getWindow();
        openComposeEmailWindow(root, "", "Fw: " + email.getSubject(), email.getContent());
    }

    @FXML
    public void replyButtonOnAction(ActionEvent actionEvent) {
        Stage root = (Stage) ((Node) actionEvent.getTarget()).getScene().getWindow();
        openComposeEmailWindow(root, email.getSender().getString(), "Re: " + email.getSubject(), email.getContent());
    }

    @FXML
    public void replyAllButtonOnAction(ActionEvent actionEvent) {
        Stage root = (Stage) ((Node) actionEvent.getTarget()).getScene().getWindow();
        String recipients = "";
        List<IAddress> recipientsList = this.email.getAllRecipients();
        for (IAddress recipient : recipientsList) {
            if (recipients.length() == 0) {
                recipients = recipient.getString();
            } else {
                recipients += ", " + recipient.getString();
            }
        }
        openComposeEmailWindow(root, recipients,"Re: " + email.getSubject(), email.getContent());
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




