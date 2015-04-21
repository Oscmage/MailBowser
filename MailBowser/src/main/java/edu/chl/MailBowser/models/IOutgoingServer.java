package edu.chl.MailBowser.models;

/**
 * Created by jesper on 2015-04-21.
 */
public interface IOutgoingServer {
    public void send(IEmail email, String username, String password);
}
