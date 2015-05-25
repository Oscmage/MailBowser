package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.account.handlers.IAccountHandler;
import edu.chl.mailbowser.email.models.IAddress;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.*;
import edu.chl.mailbowser.tag.handlers.ITagHandler;
import edu.chl.mailbowser.tag.models.ITag;
import edu.chl.mailbowser.tag.models.Tag;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by filip on 04/05/15.
 */
public class MainPresenter implements IObserver, Initializable {

    ITagHandler tagHandler = MainHandler.INSTANCE.getTagHandler();
    IAccountHandler accountHandler = MainHandler.INSTANCE.getAccountHandler();

    private IEmail email;
    private Stage root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.INSTANCE.register(this);
    }

    private void openWindow(Parent scene, String title, double sizeX, double sizeY, double posX, double posY, boolean isModal) {
        Stage stage = new Stage();
        stage.setScene(new Scene(scene, sizeX, sizeY));
        stage.setTitle(title);
        stage.setMinWidth(sizeX);
        stage.setMinHeight(sizeY);
        if(isModal) {
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(root);
        }
        stage.show();
    }

    private void openAddTagWindow() {
        openWindow(new AddTagPresenter(), "Add tag...", 200, 100, 100, 100, true);
    }

    public void openAccountManager() throws IOException {
        Parent scene = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/AccountManager.fxml"));
        openWindow(scene, "Account Manager", 400, 300, 50, 50, false);

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
        openWindow(composeEmailPresenter, "New Email...", 768, 480, 50, 50, false);
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
            case ADD_TAG_TO_EMAIL:
                tagHandler.addTagToEmail(email, (ITag)evt.getValue());
                break;
            case REMOVE_TAG_FROM_EMAIL:
                tagHandler.removeTagFromEmail(email, (ITag) evt.getValue());
                break;
            case SELECT_EMAIL:
                email = (IEmail)evt.getValue();
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
            case OPEN_ADD_TAG_WINDOW:
                openAddTagWindow();
            case MARK_EMAIL_AS_DELETED:
                tagHandler.getTagsWith(email).stream().forEach(t -> tagHandler.removeTagFromEmail(email, t));
                tagHandler.addTagToEmail(email, new Tag("Deleted"));
                break;
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