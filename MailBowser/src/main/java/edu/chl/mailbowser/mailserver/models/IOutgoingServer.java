package edu.chl.mailbowser.mailserver.models;

import edu.chl.mailbowser.email.models.IEmail;

/**
 * Created by jesper on 2015-04-21.
 */
public interface IOutgoingServer {
    public void send(IEmail email, String username, String password);
}
