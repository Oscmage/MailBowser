package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.account.factories.MailServerTypes;
import edu.chl.mailbowser.account.handlers.IAccountHandler;
import edu.chl.mailbowser.account.models.Account;
import edu.chl.mailbowser.email.models.Address;
import edu.chl.mailbowser.email.models.IAddress;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


/**
 * Created by jesper on 2015-05-18.
 */
public class AddAccountPresenter {

    private IAccountHandler accountHandler = MainHandler.INSTANCE.getAccountHandler();

    @FXML private TextField addressField;
    @FXML private PasswordField passwordField;
    @FXML private Button addAccountButton;
    @FXML private Label errorLabel;


    public void onMouseClicked(){
        IAddress address = new Address(addressField.getText());
        Account account = new Account(address
                ,passwordField.getText(),
                MailServerTypes.createIncomingServer(MailServerTypes.Type.GMAIL),
                MailServerTypes.createOutgoingServer(MailServerTypes.Type.GMAIL));
        if(!account.testConnect()){
            errorLabel.setText("Could not connect to server");
        }else if(accountHandler.getAccounts().contains(account)) {
            errorLabel.setText("This account already exists");
        } else {
            accountHandler.addAccount(account);
        }
        EventBus.INSTANCE.publish(new Event(EventType.CLOSE_THIS,this));
    }
}
