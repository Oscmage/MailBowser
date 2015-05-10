package edu.chl.mailbowser.presenters;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by filip on 04/05/15.
 */
public class TopbarPresenter {

    // This method is invoked when the "New Email"-button is pressed, and is bound via the onAction attribute
    @FXML protected void newEmailButtonActionPerformed(ActionEvent event) throws Exception {

        // Get the parent stage, simply to position our newly created window related to it
        Stage mainStage = (Stage)((Node)event.getTarget()).getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ComposeEmailView.fxml"));

        Stage newEmailStage = new Stage();
        newEmailStage.setTitle("New Email...");
        newEmailStage.setX(mainStage.getX() + 50);
        newEmailStage.setY(mainStage.getY() + 50);

        Scene scene = new Scene(root, 960, 600);

        // Add fonts and styles to the scene
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Roboto:400italic,300,700,400");

        newEmailStage.setScene(scene);
        newEmailStage.show();

    }

}

