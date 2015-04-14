package edu.chl.MailBowser.models;

/**
 * Created by jesper on 2015-04-11.
 */
public interface IMailServer {
    /**
     * This method sends the email through the outgoing server of the account.
     * @return the result of the method, true if send was successful else false
     */
    public boolean send(IEmail email, IAccount account);
}
