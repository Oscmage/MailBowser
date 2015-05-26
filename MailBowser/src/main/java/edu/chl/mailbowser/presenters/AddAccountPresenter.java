package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.account.factories.MailServerTypes;
import edu.chl.mailbowser.account.models.Account;
import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.email.models.Address;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mats on 22/05/15.
 */
public class AddAccountPresenter extends VBox implements Initializable {

    @FXML protected TextField usernameField;
    @FXML protected TextField passwordField;
    @FXML protected ChoiceBox<MailServerTypes> accountTypeChoiceBox;

    /**
     * Initializes a create account view.
     */
    public AddAccountPresenter() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddAccountView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e.getCause());
        }

        setupAccountTypeChoiceBox();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    private void setupAccountTypeChoiceBox() {
        ObservableList<MailServerTypes> observableList = FXCollections.observableArrayList();
        observableList.add(MailServerTypes.GMAIL);
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
