package edu.chl.mailbowser.account;

import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.utils.Callback;

import java.io.Serializable;

/**
 * Created by jesper on 2015-04-21.
 */
public interface IOutgoingServer extends Serializable {
    /**
     * Sends an email using the supplied credentials. When the sending has finished, the onSuccess method is called
     * in the callback with the send email as a parameter.
     *
     * @param email the email to send
     * @param username the username to use when authenticating with the physical mail server
     * @param password the password to use when authenticating with the physical mail server
     * @param callback the callback to use when the sending is finished
     */
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
