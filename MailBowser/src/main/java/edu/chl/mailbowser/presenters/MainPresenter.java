package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.main.MainHandler;
import edu.chl.mailbowser.account.IAccountHandler;
import edu.chl.mailbowser.email.IAddress;
import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.*;
import edu.chl.mailbowser.tag.ITagHandler;
import edu.chl.mailbowser.tag.ITag;
import edu.chl.mailbowser.tag.Tag;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;

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


    @FXML private MenuItem deleteMenuItem;
    @FXML private MenuItem addTagMenuItem;
    @FXML private MenuItem forwardMenuItem;
    @FXML private MenuItem replyMenuItem;
    @FXML private MenuItem replyAllMenuItem;
    @FXML private MenuItem fetchMenuItem;
    @FXML private MenuItem refetchMenuItem;
    Stage newStage;
    Stage accountManager;

    private ITagHandler tagHandler = MainHandler.INSTANCE.getTagHandler();
    private IAccountHandler accountHandler = MainHandler.INSTANCE.getAccountHandler();

    private IEmail email;
    private Stage root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.INSTANCE.register(this);
        showOrHideMenuOptions();
    }


    /**
     * Displays or hides the menu options in file/edit depending on whether an account and email exists.
     *
     * If an account doesn't exist disableAnyTypeOfFetch and
     * DisableMenuItemsThatNeedASelectedEmail both with the boolean value true.
     *
     * If an account exists fetch is enabled.
     * If also there's a email selected disableMenuItemsThatNeedASelectedEmail is set to false otherwise true.
     */
    private void showOrHideMenuOptions(){
        if(accountHandler.getAccounts().size() != 0){ // If atleast one account exists
            if(this.email != null) { // If there's an email currently selected
                disableMenuItemsThatNeedASelectedEmail(false); //Disable forward, reply etc
            } else {
                disableMenuItemsThatNeedASelectedEmail(true); //Enable forward, reply etc.
            }
            disableAnyTypeOfFetch(false); //If account exist fetch and refetch should be possible
        } else {
            disableAnyTypeOfFetch(true);
            disableMenuItemsThatNeedASelectedEmail(true);
        }
    }

    /**
     * Disables or enables the file menu options for:
     * addTag
     * forward
     * reply
     * replyAll
     * delete
     * @param b if set true you will disable.
     */
    private void disableMenuItemsThatNeedASelectedEmail(boolean b){
        addTagMenuItem.setDisable(b);
        forwardMenuItem.setDisable(b);
        replyMenuItem.setDisable(b);
        replyAllMenuItem.setDisable(b);
        deleteMenuItem.setDisable(b);
    }

    /**
     * Disables any type of fetching if set true.
     * @param b
     */
    private void disableAnyTypeOfFetch(boolean b) {
        refetchMenuItem.setDisable(b);
        fetchMenuItem.setDisable(b);
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

    /**
     * Creates a new Account Manager window
     * @throws IOException
     */
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
            case SELECT_EMAIL:
                email = (IEmail)evt.getValue();
                showOrHideMenuOptions();
                break;
            case ADD_ACCOUNT:
                showOrHideMenuOptions();
                break;
            case ADD_TAG_TO_EMAIL:
                tagHandler.addTagToEmail(email, (ITag)evt.getValue());
                break;
            case REMOVE_TAG_FROM_EMAIL:
                tagHandler.removeTagFromEmail(email, (ITag) evt.getValue());
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
    private void fetchMenuItemOnAction(ActionEvent actionEvent) {
        accountHandler.initFetchingFromAllAccounts();
    }

    @FXML
    private void refetchMenuItemOnAction(ActionEvent actionEvent) {
        accountHandler.initRefetchingFromAllAccounts();
    }

    @FXML
    private void newEmailMenuItemOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new edu.chl.mailbowser.event.Event(EventType.OPEN_COMPOSE_EMAIL_WINDOW, email));
    }

    @FXML
    private void addTagMenuItemOnAction(ActionEvent actionEvent) {
        //TODO solve when events refactoring handled.
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
        Platform.exit();
    }

    @FXML
    private void openContactBookMenuItemOnAction(ActionEvent actionEvent) {
        //TODO solve when events refactoring handled.
    }

    @FXML
    private void deleteMenuItemOnAction(ActionEvent actionEvent) {
        EventBus.INSTANCE.publish(new Event(EventType.MARK_EMAIL_AS_DELETED, email));
    }


}