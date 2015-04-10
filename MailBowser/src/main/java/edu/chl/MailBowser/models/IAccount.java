package edu.chl.MailBowser.models;

/**
 * Created by OscarEvertsson on 09/04/15.
 */
public interface IAccount {
    void setEmailAddress(EmailAddress address);
    EmailAddress getEmailAddress();
    void setPassword(String password);
    String getPassword();
    void setIncomingServer(IMailServer server);
    IMailServer getIncomingServer(IMailServer server);
    void setOutgoingServer(IMailServer server);
    IMailServer getOutgoingServer(IMailServer server);
}
