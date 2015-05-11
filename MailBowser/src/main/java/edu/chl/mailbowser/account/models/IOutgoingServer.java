package edu.chl.mailbowser.account.models;

import edu.chl.mailbowser.email.models.IEmail;

import java.io.Serializable;

/**
 * Created by jesper on 2015-04-21.
 */
public interface IOutgoingServer extends Serializable {
    void send(IEmail email, String username, String password, Callback<IEmail> callback);
}
