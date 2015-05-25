package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.email.models.IAddress;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
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
    Stage root;

    private IEmail email;


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
        if(MainHandler.INSTANCE.getAccountHandler().getAccounts().size() != 0){ // If atleast one account exists
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

    /**
     * Creates a new Account Manager window
     * @throws IOException
     */
    public void openAccountManager() throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/AccountManager.fxml"));

        accountManager = new Stage();
        accountManager.setTitle("Account Manager");
        accountManager.setScene(new Scene(fxml, 400, 300));

        accountManager.setMinWidth(400.0);
        accountManager.setMinHeight(300.0);

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
                showOrHideMenuOptions();
                break;
            case ADD_ACCOUNT:
                showOrHideMenuOptions();
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
    private void fetchMenuItemOnAction(ActionEvent actionEvent) {
        MainHandler.INSTANCE.getAccountHandler().initFetchingFromAllAccounts();
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
        EventBus.INSTANCE.publish(new Event(EventType.DELETE_EMAIL, email));
    }


}