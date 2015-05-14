package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.account.handlers.AccountHandler;
import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by filip on 04/05/15.
 */
public class TopbarPresenter {

    @FXML
    private TextField searchField;

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
    private void refetchButtonOnAction(ActionEvent event) {
        IAccount account = AccountHandler.getInstance().getAccount();
        account.refetch();
    }
}

