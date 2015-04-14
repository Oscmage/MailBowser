package edu.chl.MailBowser.controllers;

import edu.chl.MailBowser.models.Email;
import edu.chl.MailBowser.models.IAccount;
import edu.chl.MailBowser.models.IEmail;
import edu.chl.MailBowser.models.IMailServer;
import edu.chl.MailBowser.views.SendEmailView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by mats on 09/04/15.
 */
public class EmailController implements Observer {
    private SendEmailView view;

    /**
     * Constructor.
     *
     * @param view
     */
    public EmailController(SendEmailView view) {
        this.view = view;
        this.view.addObserver(this);
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
     * This method gets called when something happens in the view. It parses the event and calls the appropriate controller method.
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            String argStr = (String) arg;
            if (argStr.equals("sendButtonClicked")) {
                sendEmail();
            }
        }
    }
}
