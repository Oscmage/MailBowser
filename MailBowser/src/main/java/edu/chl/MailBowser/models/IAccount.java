package edu.chl.MailBowser.models;

/**
 * Created by OscarEvertsson on 09/04/15.
 */
public interface IAccount {
    void setAddress(IAddress address);
    IAddress getAddress();
    String getUsername();
    void setPassword(String password);
    String getPassword();
    void setIncomingServer(IMailServer server);
    IMailServer getIncomingServer();
    void setOutgoingServer(IMailServer server);
    IMailServer getOutgoingServer();
}
