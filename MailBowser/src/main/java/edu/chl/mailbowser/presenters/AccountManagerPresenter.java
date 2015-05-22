package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.account.factories.MailServerFactory;
import edu.chl.mailbowser.account.handlers.IAccountHandler;
import edu.chl.mailbowser.account.models.Account;
import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.email.models.Address;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObservable;
import edu.chl.mailbowser.event.IObserver;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by filip on 19/05/15.
 */
public class AccountManagerPresenter implements Initializable, IObserver {

    private IAccountHandler accountHandler = MainHandler.INSTANCE.getAccountHandler();

    private List<IAccount> accounts;
    private IAccount currentAccount;

    @FXML private ListView<IAccount> accountsList;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button saveButton;
    @FXML private ChoiceBox<ServerType> serverTypes;
    @FXML private TextField address;
    @FXML private TextField password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.INSTANCE.register(this);
        
        accounts = accountHandler.getAccounts();
        currentAccount = accounts.get(0);

        initializeServerTypes();
        updateAccountsList();
        updateView();
    }

    public void initializeServerTypes() {

        ObservableList<ServerType> observableList = FXCollections.observableArrayList();

        observableList.add(new ServerType(MailServerFactory.Type.GMAIL, "Gmail"));
        serverTypes.setItems(observableList);

        serverTypes.getSelectionModel().select(0);
    }

    public void updateAccountsList() {
        accounts = accountHandler.getAccounts();
        ObservableList<IAccount> observableList = FXCollections.observableArrayList();

        for(IAccount account : accounts) {
            if(!observableList.contains(account)) {
                observableList.add(account);
            }
        }
        accountsList.setItems(observableList);
    }

    @FXML
    private void updateView() {
        address.setText(currentAccount.getAddress().toString());
        password.setText(currentAccount.getPassword());
    }

    @FXML
    protected void onItemChanged() {
        currentAccount = this.accountsList.getSelectionModel().getSelectedItem();
        updateView();
    }

    @FXML
    protected void saveAccount() {
        if(address.getText().equals("") || password.getText().equals("")) {
            System.out.println("Address or password field was empty. Aborting save.");
        } else {
            currentAccount.setAddress(new Address(address.getText()));
            currentAccount.setPassword(password.getText());
        }
    }

    @FXML
    protected void addAccount() {
        currentAccount = new Account(
                new Address(""),
                "",
                MailServerFactory.createIncomingServer(MailServerFactory.Type.GMAIL),
                MailServerFactory.createOutgoingServer(MailServerFactory.Type.GMAIL)
        );
        address.setText("");
        password.setText("");
    }

    @FXML
    protected void deleteAccount() {
        int currentIndex = accountsList.getItems().indexOf(currentAccount);

        if(currentIndex > 0) {
            accountsList.getSelectionModel().select(currentIndex - 1);
            accountHandler.getAccounts().remove(currentAccount);
            updateAccountsList();
        } else {
            System.out.println("You need at least one account. Aborting deletion.");
        }
    }

    /**
     * Adds an account to the list of accounts in this view.
     *
     * @param account the account to add
     */
    public void addAccountToList(IAccount account) {
        accounts.add(account);
    }

    /**
     * Remvoes an account from the list of accounts in this view.
     *
     * @param account the account to remove
     */
    public void removeAccountFromList(IAccount account) {
        accounts.remove(account);
    }

    @Override
    public void onEvent(IEvent evt) {
        Platform.runLater(() -> {
            switch (evt.getType()) {
                case ADD_ACCOUNT:
                    addAccountToList((IAccount) evt);
                    break;
                case REMOVE_ACCOUNT:
                    removeAccountFromList((IAccount) evt);
                    break;
            }
        });
    }

    class ServerType {

        MailServerFactory.Type type;
        String name;

        ServerType(MailServerFactory.Type type, String name) {
            this.type = type;
            this.name = name;
        }

        @Override public String toString() {
            return name;
        }

    }

}
