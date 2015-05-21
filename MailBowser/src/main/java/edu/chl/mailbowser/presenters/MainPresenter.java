package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
import javafx.event.ActionEvent;
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
public class MainPresenter implements IObserver, Initializable {
    @FXML MenuItem addAccountMenuItem;
    Stage newStage;
    Stage accountManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.INSTANCE.register(this);
    }

    public void onAction() throws IOException {
        newStage = new Stage();
        Parent node = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/AddAccountView.fxml"));
        newStage.setTitle("Add Account");
        
        Scene scene = new Scene(node, node.prefWidth(0), node.prefHeight(0));

        newStage.setScene(scene);
        newStage.show();
    }

    public void openAccountManager() throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/AccountManager.fxml"));

        accountManager = new Stage();
        accountManager.setTitle("Account Manager");
        accountManager.setScene(new Scene(fxml, 400, 300));
        accountManager.show();

    }

    @Override
    public void onEvent(IEvent evt) {
        if(evt.getType()==EventType.CLOSE_THIS){
            newStage.close();
        }
    }

    public void refetchMenuItemOnAction(ActionEvent actionEvent) {
        MainHandler.INSTANCE.getAccountHandler().initRefetchingFromAllAccounts();
    }
}