package edu.chl.mailbowser.account.models;

import edu.chl.mailbowser.email.models.IEmail;

/**
 * Created by jesper on 2015-04-21.
 */
public interface IOutgoingServer {
    void send(IEmail email, String username, String password, Callback<IEmail> callback);
}
