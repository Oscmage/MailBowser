package edu.chl.mailbowser.account.models;

import edu.chl.mailbowser.email.models.IAddress;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;

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

    public Account(IAddress newAddress, String newPassword, IIncomingServer newIncomingServer, IOutgoingServer newOutgoingServer) {
        address = newAddress;
        password = newPassword;
        incomingServer = newIncomingServer;
        outgoingServer = newOutgoingServer;
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
     * Returns all emails that belong to this account.
     *
     * @return a list of all emails that belong to this account
     */
    public List<IEmail> getEmails() {
        return emails;
    }

    /**
     * Uses the outgoing server to send an email.
     *
     * @param email the email to send
     */
    @Override
    public void send(IEmail email) {
        email.setSender(address);
        outgoingServer.send(email, getUsername(), password, new Callback<IEmail>() {
            @Override
            public void onSuccess(IEmail object) {
                EventBus.INSTANCE.publish(new Event(EventType.SEND_EMAIL, object));
            }

            @Override
            public void onFailure(String msg) {
                EventBus.INSTANCE.publish(new Event(EventType.SEND_EMAIL_FAIL, msg));
            }
        });
    }

    /**
     * Fetches for new email.
     */
    public void fetch() {
        System.out.println("Account: fetch()");
        initFetch(false);
    }

    public boolean testConnect(){
        return incomingServer.testConnection(getUsername(),getPassword());
    }

    /**
     * Clears the already fetched emails and does a new fetch from a clean state.
     */
    @Override
    public void refetch() {
        System.out.println("Account: refetch()");
        emails = new ArrayList<>();
        EventBus.INSTANCE.publish(new Event(EventType.CLEAR_EMAILS, null));
        initFetch(true);
    }

    /**
     * Initiate a fetch from the server. If the cleanFetch flag is set, all mail from the server will be fetched,
     * regardless of whether they have been fetched before. If cleanFetch is not set, only emails that haven't
     * been fetched yet will be fetched from the server.
     *
     * @param cleanFetch if true, all emails will be fetched. if false, only emails that haven't been fetched before
     *                   will be fetched
     */
    private void initFetch(boolean cleanFetch) {
        incomingServer.fetch(getUsername(), password, cleanFetch, new Callback<IEmail>() {
            @Override
            public void onSuccess(IEmail object) {
                emails.add(object);
                EventBus.INSTANCE.publish(new Event(EventType.FETCH_EMAIL, object));
            }

            @Override
            public void onFailure(String msg) {
                EventBus.INSTANCE.publish(new Event(EventType.FETCH_EMAILS_FAIL, msg));
            }
        });
    }
}
