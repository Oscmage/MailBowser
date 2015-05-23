package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.email.models.IAddress;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
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
import java.util.*;

/**
 * Created by filip on 04/05/15.
 */
public class MainPresenter implements IObserver, Initializable {
    @FXML MenuItem addAccountMenuItem;
    Stage newStage;
    Stage accountManager;
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

    public void openAccountManager() throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/AccountManager.fxml"));

        accountManager = new Stage();
        accountManager.setTitle("Account Manager");
        accountManager.setScene(new Scene(fxml, 400, 300));
        accountManager.show();

    }

    private void openComposeEmailWindow(Stage root, String recipient, String subject, String content) {
        ComposeEmailPresenter composeEmailPresenter = new ComposeEmailPresenter(recipient, subject, content);

        // create a new stage
        Stage newEmailStage = new Stage();
        newEmailStage.setTitle("New Email...");
        newEmailStage.setX(root.getX()+50);
        newEmailStage.setY(root.getY() + 50);

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
            case SELECTED_EMAIL:
                this.email = (IEmail) evt.getValue();
                break;
            case CLOSE_THIS:
                newStage.close();
                break;
            case NEW_EMAIL:
                Stage stage1 = (Stage) evt.getValue();
                openComposeEmailWindow(stage1,"","","");
                break;
            case REPLY:
                Stage stage2 = (Stage) evt.getValue();
                openComposeEmailWindow(stage2, email.getSender().getString(),
                        "Re: " + email.getSubject(), email.getContent());
                break;
            case REPLY_ALL:
                Stage stage3 = (Stage) evt.getValue();
                replyAll(stage3);
                break;
            case FORWARD:
                Stage stage4 = (Stage) evt.getValue();
                openComposeEmailWindow(stage4, "", "Fw: " + email.getSubject(), email.getContent());
                break;
            case DELETE_EMAIL:

        }

    }

    private void replyAll(Stage root){
        String recipients = "";
        java.util.List<IAddress> recipientsList = this.email.getAllRecipients();
        for (IAddress recipient : recipientsList) {
            if (recipients.length() == 0) {
                recipients = recipient.getString();
            } else {
                recipients += ", " + recipient.getString();
            }
        }
        openComposeEmailWindow(root, recipients, "Re: " + email.getSubject(), email.getContent());
    }

    @FXML
    private void refetchMenuItemOnAction(ActionEvent actionEvent) {
        MainHandler.INSTANCE.getAccountHandler().initRefetchingFromAllAccounts();
    }

    @FXML
    private void newEmailMenuItemOnAction(ActionEvent actionEvent) {
        openComposeEmailWindow(getNewCenteredStage(), "", "", "");
    }

    
    @FXML
    private void addTagMenuItemOnAction(ActionEvent actionEvent) {

    }
    @FXML
    private void forwardMenuItemOnAction(ActionEvent actionEvent) {
        openComposeEmailWindow(getNewCenteredStage(), "", "Fw: " + email.getSubject(), email.getContent());
    }
    @FXML
    private void replyMenuItemOnAction(ActionEvent actionEvent) {
    }
    @FXML
    private void replyAllMenuItemOnAction(ActionEvent actionEvent) {
        replyAll(new Stage());
    }
    @FXML
    private void closeMenuItemOnAction(ActionEvent actionEvent) {
        //TODO close everything
    }
}