package edu.chl.MailBowser.models;

/**
 * Created by OscarEvertsson on 09/04/15.
 *
 * The class Account stores the EmailAddress, username, password and Mail-server for a specific Email-account.
 */
public class Account implements IAccount {
    private IAddress address;
    private String password;

    private IMailServer incomingServer;
    private IMailServer outgoingServer;

    /**
     * Constructor.
     *
     * @param address
     * @param password
     * @param incomingServer
     * @param outgoingServer
     */
    public Account(IAddress address, String password, IMailServer incomingServer, IMailServer outgoingServer) {
        this.address = address;
        this.password = password;
        this.incomingServer = incomingServer;
        this.outgoingServer = outgoingServer;
    }

    /**
     * Sets the password to a new string.
     *
     * @param password
     */
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Updates the email address for the account.
     *
     * @param emailAddress new email address
     */
    @Override
    public void setAddress(IAddress address) {
        this.address = address;
    }

    /**
     * @return email address
     */
    @Override
    public IAddress getAddress() {
        return address;
    }

    /**
     * Sets the incoming server for the account.
     *
     * @param server
     */
    @Override
    public void setIncomingServer(IMailServer server) {
        // TODO: make immutable?
        this.incomingServer = server;
    }

    /**
     * @return incoming server
     */
    @Override
    public IMailServer getIncomingServer() {
        // TODO: make immutable?
        return incomingServer;
    }

    /**
     * Sets the outgoing server for the account.
     *
     * @param server
     */
    @Override
    public void setOutgoingServer(IMailServer server) {
        // TODO: make immutable?
        this.outgoingServer = server;
    }

    /**
     * @return outgoing server
     */
    @Override
    public IMailServer getOutgoingServer() {
        // TODO: make immutable?
        return outgoingServer;
    }
}
