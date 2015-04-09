package edu.chl.MailBowser.views;

import edu.chl.MailBowser.models.Email;
import edu.chl.MailBowser.models.EmailAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by mats on 09/04/15.
 */
public class SendEmailView extends Observable {
    private List<Account> accounts = new ArrayList<>();
    private int selectedAccountIndex = -1;

    private List<EmailAddress> receivers = new ArrayList<>();
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
    public void addEmailReceiver(EmailAddress receiver) {
        receivers.add(receiver);
    }

    /**
     * Removes a receiver from the email. This method is called manually from Main.
     *
     * @param receiver the receiver to remove
     */
    public void removeEmailReceiver(EmailAddress receiver) {
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
    public void getEmail() {
        return new Email(subject, content, receivers);
    }

    /**
     * Returns the account chosen to send the mail.
     *
     * @return the chosen account
     */
    public Account getAccount() {
        return accounts.get(selectedAccountIndex);
    }
}
