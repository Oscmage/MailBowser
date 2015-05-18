package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.account.factories.MailServerFactory;
import edu.chl.mailbowser.account.handlers.AccountHandler;
import edu.chl.mailbowser.account.models.Account;
import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.email.models.Address;
import javafx.fxml.FXML;
import javafx.scene.control.*;


/**
 * Created by jesper on 2015-05-18.
 */
public class AddAccountPresenter {

    @FXML private TextField addressField;
    @FXML private PasswordField passwordField;
    @FXML private Button addAccountButton;
    @FXML private Label errorLabel;


    public void onMouseClicked(){
        AccountHandler.getInstance().setAccount(new Account(new Address(addressField.getText())
                ,passwordField.getText(),
                MailServerFactory.createIncomingServer(MailServerFactory.Type.GMAIL),
                MailServerFactory.createOutgoingServer(MailServerFactory.Type.GMAIL)));
        IAccount account = AccountHandler.getInstance().getAccount();
        if(!account.testConnect()){
            errorLabel.setText("Could not connect to server");
        }
        System.out.print(AccountHandler.getInstance());
    }
}
