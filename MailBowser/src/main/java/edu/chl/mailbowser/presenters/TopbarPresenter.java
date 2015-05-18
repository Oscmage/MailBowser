package edu.chl.mailbowser.presenters;


import edu.chl.mailbowser.account.handlers.AccountHandler;
import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.email.models.IAddress;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.*;
import edu.chl.mailbowser.tag.handlers.TagHandler;
import edu.chl.mailbowser.tag.models.Tag;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

/**
 * Created by filip on 04/05/15.
 */
public class TopbarPresenter implements IObserver {

    private IEmail email;

    public TopbarPresenter(){
        EventBus.INSTANCE.register(this);
    }

    @FXML private TextField addTagTextField;
    @FXML private TextField searchField;

    private void openComposeEmailWindow(Stage parentStage, String recipient, String subject, String content) {
        // create the component
        ComposeEmailPresenter composeEmailPresenter = new ComposeEmailPresenter(recipient, subject, content);

        // create a new stage
        Stage newEmailStage = new Stage();
        newEmailStage.setTitle("New Email...");
        newEmailStage.setX(parentStage.getX()+50);
        newEmailStage.setY(parentStage.getY()+50);

        // add the component to the stage
        newEmailStage.setScene(new Scene(composeEmailPresenter));
        newEmailStage.show();
    }

    @FXML
    private void addTagOnAction(ActionEvent actionEvent) {
        String text = addTagTextField.getText();
        TagHandler.getInstance().addTag(this.email, new Tag(text));
    }

    @Override
    public void onEvent(IEvent evt) {
        switch (evt.getType()) {
            case SELECTED_EMAIL:
                this.email = (IEmail) evt.getValue();
                break;
        }
    }

    // This method is invoked when the "New Email"-button is pressed, and is bound via the onAction attribute
    @FXML
    private void newEmailButtonActionPerformed(ActionEvent event) {
        // Get the parent stage, simply to position our newly created window related to it
        Stage mainStage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        openComposeEmailWindow(mainStage, "", "", "");
    }

    @FXML
    private void searchFieldOnAction(ActionEvent event) {
        String text = searchField.getText();
        EventBus.INSTANCE.publish(new Event(EventType.SEARCH, text));
    }

    // This method is invoked when the "Refetch"-button is pressed, ans id bound via the onAction attribute
    @FXML
    private void refetchButtonOnAction(ActionEvent actionEvent) {
        IAccount account = AccountHandler.getInstance().getAccount();
        account.refetch();
    }

    @FXML
    private void forwardButtonOnAction(ActionEvent actionEvent) {
        Stage mainStage = (Stage) ((Node) actionEvent.getTarget()).getScene().getWindow();
        openComposeEmailWindow(mainStage, "", "Fw: " + email.getSubject(), this.email.getContent());
    }

    @FXML
    private void replyButtonOnAction(ActionEvent actionEvent) {
        Stage mainStage = (Stage) ((Node) actionEvent.getTarget()).getScene().getWindow();
        openComposeEmailWindow(mainStage, this.email.getSender().getString(), "Re: " + this.email.getSubject(),
                this.email.getContent());
    }

    @FXML private void replyAllButtonOnAction(ActionEvent actionEvent) {
        Stage mainStage = (Stage) ((Node) actionEvent.getTarget()).getScene().getWindow();
        String recipientsString = "";
        List<IAddress> recipientsList = this.email.getReceivers();
        for (IAddress recipient : recipientsList) {
            if (recipientsString.length() == 0) {
                recipientsString = recipient.getString();
            } else {
                recipientsString += ", " + recipient.getString();
            }
        }
        openComposeEmailWindow(mainStage,recipientsString,"Re: " + this.email.getSubject(),this.email.getContent());
    }
}




