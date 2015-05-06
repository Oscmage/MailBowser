package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.account.handlers.AccountHandler;
import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.address.models.Address;
import edu.chl.mailbowser.address.models.IAddress;
import edu.chl.mailbowser.email.models.Email;
import edu.chl.mailbowser.email.models.IEmail;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mats on 09/04/15.
 */
public class SendEmailPresenter {

    // Assign the fields from the view to variables via the fx:id attribute
    // Note that these variables belong to the javafx.scene.control package
    @FXML private TextField receiver;
    @FXML private TextField subject;
    @FXML private TextArea content;

    // This method is invoked when the send button is pressed, and is bound via the onAction attribute
    @FXML protected void sendButtonActionPerformed(ActionEvent event) {

        // Declare receivers
        List<IAddress> receivers = new ArrayList<IAddress>();
        receivers.add(new Address(this.receiver.getText()));

        // Create a new email
        IEmail email = new Email(receivers, this.subject.getText(), this.content.getText());

        // Let's just use the "default" account, and its belonging server, for now
        IAccount account = AccountHandler.INSTANCE.getAccount(0);

        // Send the little bastard to its set recipients
        account.send(email);
    }
}
