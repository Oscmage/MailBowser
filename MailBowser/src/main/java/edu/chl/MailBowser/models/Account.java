package edu.chl.MailBowser.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OscarEvertsson on 09/04/15.
 *
 * A model class for an email account. An account has an address, a password and two mail servers - an incoming and an outgoing.
 */
public class Account implements IAccount {
    private IAddress address;
    private String password;

    private IIncomingServer incomingServer;
    private IOutgoingServer outgoingServer;

    private List<IEmail> emails = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param address
     * @param password
     * @param incomingServer
     * @param outgoingServer
     */
    public Account(IAddress address, String password, IIncomingServer incomingServer, IOutgoingServer outgoingServer) {
        this.address = address;
        this.password = password;
        this.incomingServer = incomingServer;
        this.outgoingServer = outgoingServer;
    }

    /**
     * Returns a string representation of this object.
     * The string will look like this: "Account [address=<address>, password=<password>"
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "Account [address=" + address + ", password=" + password + "]";
    }

    /**
     * Returns the username for the account as a String.
     *
     * @return username as a String
     */
    @Override
    public String getUsername() {
        return address.getString();
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
    public void setIncomingServer(IIncomingServer server) {
        // TODO: make immutable?
        this.incomingServer = server;
    }

    /**
     * @return incoming server
     */
    @Override
    public IIncomingServer getIncomingServer() {
        // TODO: make immutable?
        return incomingServer;
    }

    /**
     * Sets the outgoing server for the account.
     *
     * @param server
     */
    @Override
    public void setOutgoingServer(IOutgoingServer server) {
        // TODO: make immutable?
        this.outgoingServer = server;
    }

    /**
     * @return outgoing server
     */
    @Override
    public IOutgoingServer getOutgoingServer() {
        // TODO: make immutable?
        return outgoingServer;
    }

    /**
     * Uses the outgoing server to send an email.
     *
     * @param email the email to send
     */
    @Override
    public void send(IEmail email) {
        email.setSender(address);
        outgoingServer.send(email, getUsername(), password);
    }

    /**
     * Fetches for new email.
     */
    public void fetch() {
        this.emails = incomingServer.fetch(getUsername(), password);
    }
}
