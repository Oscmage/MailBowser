package edu.chl.mailbowser.account.models;

import edu.chl.mailbowser.email.models.IEmail;

import java.io.Serializable;

/**
 * Created by jesper on 2015-04-21.
 *
 * An interface for mail servers that handle incoming mail.
 */
public interface IIncomingServer extends Serializable {
    /**
     * Fetches all emails from the server, using the supplied username and password.
     * callbacks onSuccess method will be called whenever a single email has been fetched.
     *
     * @param username the username to authenticate with
     * @param password the password to authenticate with
     * @param cleanFetch if true, the processed flag will be cleared from all emails on the server, and all emails
     *                   will be fetched again. If false, only emails that haven't been fetched before will be fetched.
     * @param callback the callback to use when an email has been fetched from the server. The first element of
     *                 the pair will be the email that was fetched, and the second will be the name of the folder it was fetched from
     */
    void fetch(String username, String password, boolean cleanFetch, Callback<Pair<IEmail, String>> callback);

    /**
     * Tests if a connection can be made to the mail server with the given login credentials.
     *
     * @param username the username to authenticate with
     * @param password the password to authenticate with
     * @return true if the connection is successful, otherwise false
     */
    boolean testConnection(String username, String password);
}
