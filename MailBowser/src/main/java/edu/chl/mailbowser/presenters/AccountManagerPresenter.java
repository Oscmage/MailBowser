package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.account.factories.MailServerFactory;
import edu.chl.mailbowser.account.handlers.IAccountHandler;
import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObservable;
import edu.chl.mailbowser.event.IObserver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by filip on 19/05/15.
 */
public class AccountManagerPresenter implements Initializable {

    private IAccountHandler accountHandler = MainHandler.INSTANCE.getAccountHandler();

    @FXML private ListView<IAccount> accountsList;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button saveButton;
    @FXML private ChoiceBox serverTypes;
    @FXML private TextField address;
    @FXML private TextField password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<IAccount> observableList = FXCollections.observableArrayList();

        for(IAccount account : accountHandler.getAccounts()) {
            if(!observableList.contains(account)) {
                observableList.add(account);
            }
        }

        accountsList.setItems(observableList);
    }
}
