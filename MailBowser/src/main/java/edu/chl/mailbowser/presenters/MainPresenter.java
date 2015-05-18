package edu.chl.mailbowser.presenters;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by filip on 04/05/15.
 */
public class MainPresenter {
    @FXML MenuItem addAccountMenuItem;

    public void onAction() throws IOException {
        Stage newStage = new Stage();
        Parent node = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/AddAccountView.fxml"));
        newStage.setTitle("Add Account");
        
        Scene scene = new Scene(node, node.prefWidth(0), node.prefHeight(0));

        newStage.setScene(scene);
        newStage.show();
    }
}