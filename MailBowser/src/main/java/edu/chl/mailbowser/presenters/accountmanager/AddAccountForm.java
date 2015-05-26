package edu.chl.mailbowser.presenters.accountmanager;

import edu.chl.mailbowser.account.MailServerTypes;
import edu.chl.mailbowser.account.Account;
import edu.chl.mailbowser.account.IAccount;
import edu.chl.mailbowser.email.Address;
import edu.chl.mailbowser.main.MainHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by mats on 22/05/15.
 */
public class AddAccountForm extends VBox {

    @FXML protected TextField usernameField;
    @FXML protected TextField passwordField;
    @FXML protected ChoiceBox<MailServerTypes> accountTypeChoiceBox;

    /**
     * Initializes a create account view.
     */
    public AddAccountForm() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddAccountView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e.getCause());
        }

        initializeAccountTypeChoiceBox();
    }

    /**
     * Populates the choice box with all supported server types.
     */
    private void initializeAccountTypeChoiceBox() {
        ObservableList<MailServerTypes> observableList = FXCollections.observableArrayList();
        for(MailServerTypes mailServerTypes : MailServerTypes.values()) {
            observableList.add(mailServerTypes);
        }
        accountTypeChoiceBox.setItems(observableList);
        accountTypeChoiceBox.getSelectionModel().select(0);
    }

    /**
     * Creates a new account with the information supplied in the view and returns it.
     *
     * @return the created account
     */
    public IAccount createAccount() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        MailServerTypes serverType = accountTypeChoiceBox.getSelectionModel().getSelectedItem();

        return new Account(
                new Address(username),
                password,
                serverType.createIncomingServer(),
                serverType.createOutgoingServer(),
                MainHandler.INSTANCE.getTagHandler()
        );
    }
}
