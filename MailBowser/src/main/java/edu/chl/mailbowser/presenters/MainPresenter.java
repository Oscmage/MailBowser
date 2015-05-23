package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.email.models.IAddress;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by filip on 04/05/15.
 */
public class MainPresenter implements IObserver, Initializable {
    @FXML MenuItem addAccountMenuItem;
    Stage newStage;
    Stage accountManager;
    Stage root;
    private IEmail email;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.INSTANCE.register(this);
    }

    private Stage getNewCenteredStage(){
        Stage stage = new Stage();
        stage.setY(100);
        stage.setX(100);
        return stage;
    }

    private void openAddTagWindow() {

    }

    public void openAccountManager() throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/AccountManager.fxml"));

        accountManager = new Stage();
        accountManager.setTitle("Account Manager");
        accountManager.setScene(new Scene(fxml, 400, 300));
        accountManager.show();
    }

    private void openComposeEmailWindow(List<IAddress> recipients, String subject, String content) {
        String recipientsString = "";
        java.util.List<IAddress> recipientsList = this.email.getAllRecipients();
        for (IAddress recipient : recipientsList) {
            if (recipientsString.length() == 0) {
                recipientsString = recipient.getString();
            } else {
                recipientsString += ", " + recipient.getString();
            }
        }
        openComposeEmailWindow(recipientsString, "Re: " + email.getSubject(), email.getContent());
    }

    private void openComposeEmailWindow(String recipients, String subject, String content) {
        ComposeEmailPresenter composeEmailPresenter = new ComposeEmailPresenter(recipients, subject, content);

        // create a new stage
        Stage newEmailStage = new Stage();
        newEmailStage.setTitle("New Email...");

        newEmailStage.setY(root.getY() + 50);
        newEmailStage.setX(root.getX() + 50);

        // add the component to the stage
        newEmailStage.setScene(new Scene(composeEmailPresenter));
        newEmailStage.show();
    }

    @Override
    public void onEvent(IEvent evt) {
        Platform.runLater( // JavaFX can get thread problems otherwise
                () -> handleEvent(evt)
        );
    }

    private void handleEvent(IEvent evt){
        switch (evt.getType()) {
            case FXML_LOADED:
                root = (Stage)evt.getValue();
                break;
            case SELECTED_EMAIL:
                email = (IEmail)evt.getValue();
                break;
            case CLOSE_THIS:
                newStage.close();
                break;
            case OPEN_COMPOSE_EMAIL_WINDOW:
                email = (IEmail)evt.getValue();
                openComposeEmailWindow("", "", "");
                break;
            case OPEN_COMPOSE_EMAIL_WINDOW_REPLY:
                email = (IEmail)evt.getValue();
                openComposeEmailWindow(email.getSender().getString(),
                        "Re: " + email.getSubject(), "");
                break;
            case OPEN_COMPOSE_EMAIL_WINDOW_REPLY_ALL:
                email = (IEmail)evt.getValue();
                openComposeEmailWindow(email.getAllRecipients(), "Re: " + email.getSubject(), "");
                break;
            case OPEN_COMPOSE_EMAIL_WINDOW_FORWARD:
                email = (IEmail)evt.getValue();
                openComposeEmailWindow("", "Fw: " + email.getSubject(), email.getContent());
                break;
            case DELETE_EMAIL:

        }
    }

    @FXML
    private void refetchMenuItemOnAction(ActionEvent actionEvent) {
        MainHandler.INSTANCE.getAccountHandler().initRefetchingFromAllAccounts();
    }

    @FXML
    private void newEmailMenuItemOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new edu.chl.mailbowser.event.Event(EventType.OPEN_COMPOSE_EMAIL_WINDOW, email));
    }

    @FXML
    private void addTagMenuItemOnAction(ActionEvent actionEvent) {
    }

    @FXML
    private void forwardMenuItemOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.OPEN_COMPOSE_EMAIL_WINDOW_FORWARD, email));
    }
    @FXML
    private void replyMenuItemOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.OPEN_COMPOSE_EMAIL_WINDOW_REPLY, email));
    }
    @FXML
    private void replyAllMenuItemOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.OPEN_COMPOSE_EMAIL_WINDOW_REPLY_ALL, email));
    }
    @FXML
    private void closeMenuItemOnAction(ActionEvent actionEvent) {
        //TODO close everything
    }
}