package edu.chl.MailBowser;

/**
 * Created by filip on 09/04/15.
 */
public abstract class MailServer {
    /**
     * This method sends the email through the outgoing server of the account.
     * @return the result of the method, true if send was successful else false
     */
    public abstract boolean send(Email email, Account account);
}
