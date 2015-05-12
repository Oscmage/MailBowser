package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by filip on 04/05/15.
 */
public class TopbarPresenter implements Initializable {

    @FXML
    private TextField searchField;

    @FXML private Button forwardButton;

    // This method is invoked when the "New Email"-button is pressed, and is bound via the onAction attribute
    @FXML
    protected void newEmailButtonActionPerformed(ActionEvent event) {
        // Get the parent stage, simply to position our newly created window related to it
        Stage mainStage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        openComposeEmailWindow(mainStage, "", "", "");
    }

    @FXML
    public void searchFieldOnAction(ActionEvent event) {
        String text = searchField.getText();
        EventBus.INSTANCE.publish(new Event(EventType.SEARCH, text));
    }

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //this.forwardButton.setText(new String("\uf112"));
    }
}

