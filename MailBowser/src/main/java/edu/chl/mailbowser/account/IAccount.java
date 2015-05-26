package edu.chl.mailbowser.account;

import edu.chl.mailbowser.email.IAddress;
import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.tag.ITagHandler;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by OscarEvertsson on 09/04/15.
 *
 * An interface for classes that models email accounts. An account has an address, a password and two mail servers - an incoming and an outgoing.
 */
public interface IAccount extends Serializable {
    /**
     * Returns the address that is associated with this account.
     *
     * @return the address that is associated with this account
     */
    IAddress getAddress();

    /**
     * Returns the username for this account.
     *
     * @return the username as a String
     */
    String getUsername();

    /**
     * Returns the password for this account.
     *
     * @return the password as a String
     */
    String getPassword();

    /**
     * Returns the incoming server associated with this account.
     *
     * @return the incoming server associated with this account
     */
    IIncomingServer getIncomingServer();

    /**
     * Returns the outgoing server associated with this account.
     *
     * @return the outgoing server associated with this account
     */
    IOutgoingServer getOutgoingServer();

    /**
     * Returns a set of all the emails that have been fetched to this account.
     *
     * @return a set of all the emails that have been fetched to this account
     */
    Set<IEmail> getEmails();

    /**
     * Returns this accounts tag handler.
     *
     * @return this accounts tag handler
     */
    ITagHandler getTagHandler();

    /**
     * Sets the address that is associated with this account.
     *
     * @param address the address to use
     */
    void setAddress(IAddress address);

    /**
     * Sets the password to use for this account
     *
     * @param password the new password to use when connecting to mail servers using this account
     */
    void setPassword(String password);

    /**
     * Sets the server to use when fetching new emails for this account.
     *
     * @param server the server to use
     */
    void setIncomingServer(IIncomingServer server);

    /**
     * Sets the server to use when sending emails from this account.
     *
     * @param server the server to use
     */
    void setOutgoingServer(IOutgoingServer server);

    /**
     * Sets the tag handler to use when tagging fetched emails.
     *
     * @param tagHandler the tag handler to use
     */
    void setTagHandler(ITagHandler tagHandler);

    /**
     * Sends an email using this accounts outgoing server. When authenticating with the physical mail server
     * this accounts password and username are used.
     *
     * This method also sets the sender of the email to this accounts address.
     *
     * @param email the email to send
     */
    void send(IEmail email);

    /**
     * Fetches new emails that haven't been fetched before using this accounts incoming server. When authenticating
     * with the physical mail server this accounts password and username are used.
     *
     * This method doesn't return anything, it only initiates the fetching. It is up to each
     * implementation to take care of the result of the fetch.
     */
    void fetch();

    /**
     * Fetches all emails, whether or not they have been fetched before using this accounts incoming server.
     * When authenticating with the physical mail server this accounts password and username are used.
     *
     * This method doesn't return anything, it only initiates the fetching. It is up to each implementation to
     * take care of the result of the fetch.
     */
    void refetch();

    /**
     * Tests if a connection can be established to the accounts mail servers.
     *
     * @return true if a connection can be established, otherwise false
     */
    boolean testConnect();
}
