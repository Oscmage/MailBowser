package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.account.IAccount;
import edu.chl.mailbowser.account.IncomingServer;
import edu.chl.mailbowser.account.OutgoingServer;
import edu.chl.mailbowser.email.Address;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mats on 22/05/15.
 */
public class EditAccountPresenter extends VBox implements Initializable {

    private IAccount account;

    @FXML protected TextField usernameField;
    @FXML protected TextField passwordField;
    @FXML protected TextField incomingServerUrlField;
    @FXML protected TextField incomingServerPortField;
    @FXML protected TextField outgoingServerUrlField;
    @FXML protected TextField outgoingServerPortField;

    /**
     * Initializes an edit account view with a specified account.
     *
     * @param account the account to edit
     */
    public EditAccountPresenter(IAccount account) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EditAccountView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e.getCause());
        }

        this.account = account;
        updateView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    /**
     * Updates the selected account with the new info from the view.
     */
    public void updateAccountInfo() {
        String address = usernameField.getText();
        String password = passwordField.getText();
        String incomingServerUrl = incomingServerUrlField.getText();
        String incomingServerPort = incomingServerPortField.getText();
        String outgoingServerUrl = outgoingServerUrlField.getText();
        String outgoingServerPort = outgoingServerPortField.getText();

        this.account.setAddress(new Address(address));
        this.account.setPassword(password);
        this.account.setIncomingServer(new IncomingServer(incomingServerUrl, incomingServerPort));
        this.account.setOutgoingServer(new OutgoingServer(outgoingServerUrl, outgoingServerPort));
    }

    /**
     * Updates this view with the info of the current account.
     */
    public void updateView() {
        this.usernameField.setText(this.account.getUsername());
        this.passwordField.setText(this.account.getPassword());
        this.incomingServerUrlField.setText(this.account.getIncomingServer().getHostname());
        this.incomingServerPortField.setText(this.account.getIncomingServer().getPort());
        this.outgoingServerUrlField.setText(this.account.getOutgoingServer().getHostname());
        this.outgoingServerPortField.setText(this.account.getOutgoingServer().getPort());
    }

    /**
     * Validates the fields for address and password.
     *
     * @return false if any fields are empty, otherwise true
     */
    //private boolean validateFields() {
    //    if (address.getText().isEmpty() || password.getText().isEmpty()) {
    //        return false;
    //    }
    //    return true;
    //}
}
