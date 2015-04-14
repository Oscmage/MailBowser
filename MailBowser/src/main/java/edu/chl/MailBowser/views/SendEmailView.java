package edu.chl.MailBowser.views;

import edu.chl.MailBowser.models.Email;
import edu.chl.MailBowser.models.EmailAddress;
import edu.chl.MailBowser.models.IAccount;
import edu.chl.MailBowser.models.IAddress;
import edu.chl.MailBowser.models.IEmail;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by mats on 09/04/15.
 */
public class SendEmailView extends Observable {
    private List<IAccount> accounts = new ArrayList<>();
    private int selectedAccountIndex = -1;

    private List<IAddress> receivers = new ArrayList<>();
    private String subject;
    private String content;

    /**
     * Method to simulate a button press. This method is called manually from Main.
     */
    public void sendEmailButtonClicked() {
        setChanged();
        notifyObservers("sendButtonClicked");
    }

    /**
     * Sets the subject of the email. This method is called manually from Main.
     *
     * @param subject the new subject for the email
     */
    public void setEmailSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Sets the content of the email. This method is called manually from Main.
     *
     * @param content the new content for the email
     */
    public void setEmailContent(String content) {
        this.content = content;
    }

    /**
     * Adds a new receiver to the email. This method is called manually from Main.
     *
     * @param receiver the receiver to add
     */
    public void addEmailReceiver(IAddress receiver) {
        receivers.add(receiver);
    }

    /**
     * Removes a receiver from the email. This method is called manually from Main.
     *
     * @param receiver the receiver to remove
     */
    public void removeEmailReceiver(IAddress receiver) {
        receivers.remove(receiver);
    }

    /**
     * Chooses the account with the given index to be the sender of the email.
     *
     * @param index the index for the chosen account
     */
    public void chooseAccount(int index) {
        selectedAccountIndex = index;
    }

    /**
     * Returns the email in it's current form.
     *
     * @return the email associated with this view
     */
    public IEmail getEmail() {
        return new Email(receivers, subject, content);
    }

    /**
     * Returns the account chosen to send the mail.
     *
     * @return the chosen account
     */
    public IAccount getAccount() {
        return accounts.get(selectedAccountIndex);
    }
}
