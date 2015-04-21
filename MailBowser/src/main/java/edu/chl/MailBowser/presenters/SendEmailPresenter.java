package edu.chl.MailBowser.presenters;

import edu.chl.MailBowser.DataHandler;
import edu.chl.MailBowser.models.*;
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

    // Get the DataHandler
    private DataHandler dh = DataHandler.getInstance();

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
        IAccount account = dh.getAccounts().get(0);
        IMailServer server = account.getOutgoingServer();

        // Send the little bastard to its set recipients
        server.send(email, account);
    }

}
