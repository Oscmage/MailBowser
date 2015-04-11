package edu.chl.MailBowser.models;

/**
 * Created by jesper on 2015-04-11.
 */
public interface IMailServer {
    public boolean send(Email email, Account account);
}
