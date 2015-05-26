package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.main.MainHandler;
import edu.chl.mailbowser.account.IAccountHandler;
import edu.chl.mailbowser.account.IAccount;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by filip on 19/05/15.
 */
public class AccountManagerPresenter extends VBox implements IObserver {

    /**
     * An enum for keeping track of what view is being shown.
     */
    private enum Mode {
        ADD,
        EDIT
    }
    private Mode currentMode;
    private ObservableList<IAccount> accountsListItems = FXCollections.observableArrayList();
    private IAccountHandler accountHandler = MainHandler.INSTANCE.getAccountHandler();
    private IAccount selectedAccount;

    @FXML protected Button addAccountButton;
    @FXML protected Button deleteAccountButton;
    @FXML protected Button saveAccountButton;
    @FXML protected ScrollPane accountForm;
    @FXML protected ListView<IAccount> accountsList;

    public AccountManagerPresenter() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AccountManager.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println(e);
        }

        EventBus.INSTANCE.register(this);

        initializeAccountsList();

        // if there are accounts, select the first one in the list and show the edit account view
        if (accountsListItems.size() > 0) {
            selectedAccount = accountsListItems.get(0);
            showEditAccountView(selectedAccount);
        } else {
            showAddAccountView();
        }
    }

    /**
     * Initializes the list of accounts. Gets all accounts from the account handler and puts them in the list.
     */
    private void initializeAccountsList() {
        accountsList.setItems(accountsListItems);

        List<IAccount> accounts = accountHandler.getAccounts();
        for (IAccount account : accounts) {
            accountsListItems.add(account);
        }

        accountsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedAccount = newValue;
                showEditAccountView(selectedAccount);
            }
        });
    }

    /**
     * Switches to the edit account view in the right hand pane
     *
     * @param account the account to create an edit view for
     */
    private void showEditAccountView(IAccount account) {
        accountForm.setContent(new EditAccountPresenter(account));
        currentMode = Mode.EDIT;
    }

    /**
     * Switches to the add account view in the right hand pane.
     */
    private void showAddAccountView() {
        accountForm.setContent(new AddAccountPresenter());
        currentMode = Mode.ADD;
    }

    /**
     * Adds an account to the list of accounts in this view.
     *
     * @param account the account to add
     */
    private void addAccountToList(IAccount account) {
        accountsListItems.add(account);
    }

    /**
     * Remvoes an account from the list of accounts in this view.
     *
     * @param account the account to remove
     */
    private void removeAccountFromList(IAccount account) {
        accountsListItems.remove(account);

        // if there are no accounts left, show the add account view
        if (accountsListItems.size() == 0) {
            showAddAccountView();
        }
    }

    /**
     * Invoked when the "save"-button is clicked.
     *
     * @param actionEvent
     */
    @FXML
    protected void saveAccountButtonOnAction(ActionEvent actionEvent) {
        switch (currentMode) {
            case ADD:
                AddAccountPresenter addAccountPresenter = (AddAccountPresenter) accountForm.getContent();
                IAccount account = addAccountPresenter.createAccount();
                accountHandler.addAccount(account);
                break;
            case EDIT:
                EditAccountPresenter editAccountPresenter = (EditAccountPresenter) accountForm.getContent();
                editAccountPresenter.updateAccountInfo();
                editAccountPresenter.updateAccountInfo();
                break;
        }
    }

    /**
     * Invoked when the "add account"-button is clicked.
     *
     * @param actionEvent
     */
    @FXML
    protected void addAccountButtonOnAction(ActionEvent actionEvent) {
        accountsList.getSelectionModel().clearSelection();
        showAddAccountView();
    }

    /**
     * Invoked when the "Delete account"-button is clicked.
     *
     * @param actionEvent
     */
    @FXML
    protected void deleteAccountButtonOnAction(ActionEvent actionEvent) {
        accountHandler.removeAccount(selectedAccount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(IEvent evt) {
        Platform.runLater( //JavaFX can get thread problems otherwise
                () -> handleEvent(evt)
        );
    }

    private void handleEvent(IEvent evt) {
        switch (evt.getType()) {
            case ACCOUNT_ADDED:
                addAccountToList((IAccount) evt.getValue());
                break;
            case ACCOUNT_REMOVED:
                removeAccountFromList((IAccount) evt.getValue());
                break;
        }
    }
}
