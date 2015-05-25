package edu.chl.mailbowser.account;

import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.utils.Callback;

import java.io.Serializable;

/**
 * Created by jesper on 2015-04-21.
 */
public interface IOutgoingServer extends Serializable {
    void send(IEmail email, String username, String password, Callback<IEmail> callback);

    /**
     * Returns the hostname for this mail server.
     *
     * @return the hostname for this mail server
     */
    String getHostname();

    /**
     * Returns the post for this mail server.
     *
     * @return the hostname for this mail server
     */
    String getPort();
}
