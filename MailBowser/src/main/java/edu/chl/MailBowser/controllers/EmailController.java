package edu.chl.MailBowser.controllers;

import edu.chl.MailBowser.models.IAccount;
import edu.chl.MailBowser.models.IEmail;
import edu.chl.MailBowser.models.IMailServer;
import edu.chl.MailBowser.views.SendEmailView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;

/**
 * Created by mats on 09/04/15.
 */
public class EmailController implements PropertyChangeListener {
    private SendEmailView view;

    /**
     * Constructor.
     *
     * @param view
     */
    public EmailController(SendEmailView view) {
        this.view = view;
        this.view.addPropertyChangeListener(this);
    }

    /**
     * Fetches the email and account from the view, and tells the mailserver to send the email.
     */
    private void sendEmail() {
        IEmail email = view.getEmail();
        IAccount account = view.getAccount();
        IMailServer server = account.getOutgoingServer();
        server.send(email, account);
    }

    /**
     * This method gets called when something happens in a view that the controller listens to.
     * It parses the event and calls the appropriate controller method.
     *
     * @param evt an object containing information about the event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("sendButtonClicked")) {
            sendEmail();
        }
    }
}
