package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.account.handlers.IAccountHandler;
import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by filip on 19/05/15.
 */
public class AccountManagerPresenter implements Initializable, IObserver {

    /**
     * An enum for keeping track of what view is being shown.
     */
    private enum Mode {
        ADD,
        EDIT
    }
    private Mode currentMode;

    private EditAccountPresenter editAccountView;
    private AddAccountPresenter addAccountView;

    private IAccountHandler accountHandler = MainHandler.INSTANCE.getAccountHandler();

    @FXML private ListView<IAccount> accountsList;
    private ObservableList<IAccount> accountsListItems = FXCollections.observableArrayList();

    private IAccount selectedAccount;

    @FXML public Button addAccountButton;
    @FXML public Button deleteAccountButton;
    @FXML public Button saveAccountButton;

    @FXML public Pane rightHandPane;

    /**
     * Initializes the account manager. Registers to the event bus and updates all views to show current data.
     *
     * @param location {@inheritDoc}
     * @param resources {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.INSTANCE.register(this);

        initializeAccountsList();

        // if there are accounts, select the first one in the list and show the edit account view
        if (accountsListItems.size() > 0) {
            selectedAccount = accountsListItems.get(0);
            showEditAccountView(selectedAccount);
            //updateView();
        } else {
            showAddAccountView();
        }
    }

    /**
     * Initializes the list of accounts. Gets all accounts from the account handler and puts them in the list.
     */
    public void initializeAccountsList() {
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
    public void showEditAccountView(IAccount account) {
        ObservableList<Node> children = rightHandPane.getChildren();
        children.clear();
        editAccountView = new EditAccountPresenter(account);
        children.add(editAccountView);
        currentMode = Mode.EDIT;
    }

    /**
     * Switches to the add account view in the right hand pane.
     */
    public void showAddAccountView() {
        ObservableList<Node> children = rightHandPane.getChildren();
        children.clear();
        addAccountView = new AddAccountPresenter();
        children.add(addAccountView);
        currentMode = Mode.ADD;
    }

    @FXML
    protected void saveAccountButtonOnAction(ActionEvent actionEvent) {
        switch (currentMode) {
            case ADD:
                IAccount account = addAccountView.createAccount();
                accountHandler.addAccount(account);
                break;
            case EDIT:
                editAccountView.updateAccountInfo();
                break;
        }
    }

    @FXML
    protected void addAccountButtonOnAction(ActionEvent actionEvent) {
        accountsList.getSelectionModel().clearSelection();
        showAddAccountView();
    }

    @FXML
    protected void deleteAccountButtonOnAction(ActionEvent actionEvent) {
        accountHandler.removeAccount(selectedAccount);
    }

    /**
     * Adds an account to the list of accounts in this view.
     *
     * @param account the account to add
     */
    public void addAccountToList(IAccount account) {
        accountsListItems.add(account);
    }

    /**
     * Remvoes an account from the list of accounts in this view.
     *
     * @param account the account to remove
     */
    public void removeAccountFromList(IAccount account) {
        accountsListItems.remove(account);

        // if there are no accounts left, show the add account view
        if (accountsListItems.size() == 0) {
            showAddAccountView();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(IEvent evt) {
        Platform.runLater(() -> {
            switch (evt.getType()) {
                case ADD_ACCOUNT:
                    addAccountToList((IAccount) evt.getValue());
                    break;
                case REMOVE_ACCOUNT:
                    removeAccountFromList((IAccount) evt.getValue());
                    break;
            }
        });
    }
}
