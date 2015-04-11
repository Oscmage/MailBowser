package edu.chl.MailBowser.models;

/**
 * Created by OscarEvertsson on 09/04/15.
 *
 * A model class for an email account. An account has an address, a password and two mail servers - an incoming and an outgoing.
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
     * @param address new address
     */
    @Override
    public void setAddress(IAddress address) {
        this.address = address;
    }

    /**
     * @return address
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
