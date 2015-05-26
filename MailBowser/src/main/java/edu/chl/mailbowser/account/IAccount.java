package edu.chl.mailbowser.account;

import edu.chl.mailbowser.email.IAddress;
import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.tag.handlers.ITagHandler;

import java.io.Serializable;
import java.util.List;

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
    List<IEmail> getEmails();
    void refetch();
    boolean testConnect();
    void setTagHandler(ITagHandler tagHandler);
}
