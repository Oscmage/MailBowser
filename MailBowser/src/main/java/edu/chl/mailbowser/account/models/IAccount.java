package edu.chl.mailbowser.account.models;

import edu.chl.mailbowser.email.models.IAddress;
import edu.chl.mailbowser.email.models.IEmail;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by OscarEvertsson on 09/04/15.
 */
public interface IAccount extends Serializable {
    void setAddress(IAddress address);
    IAddress getAddress();
    String getUsername();
    void setPassword(String password);
    String getPassword();
    void setIncomingServer(IIncomingServer server);
    IIncomingServer getIncomingServer();
    void setOutgoingServer(IOutgoingServer server);
    IOutgoingServer getOutgoingServer();
    void send(IEmail email);
    void fetch();
    Set<IEmail> getEmails();
    void refetch();
    boolean testConnect();
}
