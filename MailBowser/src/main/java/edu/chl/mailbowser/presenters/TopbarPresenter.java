package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.account.handlers.IAccountHandler;
import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.email.models.IAddress;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.*;
import edu.chl.mailbowser.tag.handlers.ITagHandler;
import javafx.application.Platform;
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
import java.util.List;
import java.util.ResourceBundle;

public class TopbarPresenter implements IObserver, Initializable {

    private IEmail email;
    private ITagHandler tagHandler = MainHandler.INSTANCE.getTagHandler();
    private IAccountHandler accountHandler = MainHandler.INSTANCE.getAccountHandler();

    @FXML private TextField addTagTextField;
    @FXML private TextField searchField;
    @FXML private Button forwardButton;
    @FXML private Button replyButton;
    @FXML private Button replyAllButton;
    @FXML private Button fetchButton;
    @FXML private Button addTagButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.INSTANCE.register(this);
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
    public void tagButtonOnAction(ActionEvent event) {
        Stage root = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        AddTagPresenter addTagPresenter = new AddTagPresenter(root, email);

        Stage addTagStage = new Stage();
        addTagStage.setTitle("Add tag...");

        addTagStage.initModality(Modality.APPLICATION_MODAL);
        addTagStage.initOwner(root);

        addTagStage.setScene(new Scene(addTagPresenter, 300, 200));
        addTagStage.show();

    }

    @Override
    public void onEvent(IEvent evt) {
        Platform.runLater( // JavaFX can get thread problems otherwise
                () -> handleEvent(evt)
        );
    }

    private void handleEvent(IEvent evt){
        switch (evt.getType()) {
            case SELECTED_EMAIL:
                this.email = (IEmail) evt.getValue();
                break;
        }
    }

    @FXML
    public void searchFieldOnAction(ActionEvent event) {
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
    public void newButtonOnAction(ActionEvent event) {
        Stage root = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        openComposeEmailWindow(root, "", "", "");
    }

    @FXML
    public void forwardButtonOnAction(ActionEvent event) {
        Stage root = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        openComposeEmailWindow(root, "", "Fw: " + email.getSubject(), email.getContent());
    }

    @FXML
    public void replyButtonOnAction(ActionEvent event) {
        Stage root = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        openComposeEmailWindow(root, email.getSender().getString(), "Re: " + email.getSubject(), email.getContent());
    }

    @FXML
    public void replyAllButtonOnAction(ActionEvent event) {
        Stage root = (Stage) ((Node) event.getTarget()).getScene().getWindow();
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

}




