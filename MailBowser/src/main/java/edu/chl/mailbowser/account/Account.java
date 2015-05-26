package edu.chl.mailbowser.account;

import edu.chl.mailbowser.email.IAddress;
import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.tag.ITagHandler;
import edu.chl.mailbowser.tag.ITag;
import edu.chl.mailbowser.tag.Tag;
import edu.chl.mailbowser.utils.Callback;
import edu.chl.mailbowser.utils.Pair;
import edu.chl.mailbowser.utils.Crypto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OscarEvertsson on 09/04/15.
 *
 * A model class for an email account. An account has an address, a password and two mail servers - an incoming and an outgoing.
 */
public class Account implements IAccount {
    private IAddress address;

    // the key to use when encrypting and decrypting the password. the password is never saved in plain text, it is
    // only saved as an encrypted byte array
    private static final String KEY = "%*tR7sfa";
    private byte[] password;

    private IIncomingServer incomingServer;
    private IOutgoingServer outgoingServer;
    private transient ITagHandler tagHandler;

    private List<IEmail> emails = new ArrayList<>();

    public Account(IAddress address, String password, IIncomingServer incomingServer, IOutgoingServer outgoingServer,
                   ITagHandler tagHandler) {
        this.address = address;
        setPassword(password);
        this.incomingServer = incomingServer;
        this.outgoingServer = outgoingServer;
        this.tagHandler = tagHandler;
    }

    /**
     * Returns a string representation of this object.
     * The string will look like this: "address@example.com"
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return address.toString();
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
        this.password = Crypto.encryptString(password, KEY);
    }

    /**
     * @return password
     */
    @Override
    public String getPassword() {
        return Crypto.decryptByteArray(password, KEY);
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
    @Override
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
        outgoingServer.send(email, getUsername(), getPassword(), new Callback<IEmail>() {
            @Override
            public void onSuccess(IEmail object) {
                EventBus.INSTANCE.publish(new Event(EventType.SEND_EMAIL, object));
                fetch();
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
    @Override
    public void fetch() {
        System.out.println("Account: fetch()");
        initFetch(false);
    }

    @Override
    public boolean testConnect(){
        return incomingServer.testConnection(getUsername(),getPassword());
    }

    @Override
    public void setTagHandler(ITagHandler tagHandler) {
        this.tagHandler = tagHandler;
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
     * A callback will arrive from the incoming server whenever an email has been fetched. This method tags the email
     * with the name of its folder.
     *
     * @param cleanFetch if true, all emails will be fetched. if false, only emails that haven't been fetched before
     *                   will be fetched
     */
    private void initFetch(boolean cleanFetch) {
        incomingServer.fetch(getUsername(), getPassword(), cleanFetch, new Callback<Pair<IEmail, String>>() {
            @Override
            public void onSuccess(Pair<IEmail, String> object) {
                IEmail email = object.getFirst();
                String folderName = object.getSecond();
                ITag tag = new Tag(folderName);

                emails.add(email);
                tagHandler.addTagToEmail(email, tag);

                EventBus.INSTANCE.publish(new Event(EventType.FETCHED_EMAIL, email));
            }

            @Override
            public void onFailure(String msg) {
                EventBus.INSTANCE.publish(new Event(EventType.FETCHED_ALL_EMAILS_FAILED, msg));
            }
        });
    }

    /**
     * {@inheritDoc}
     *
     * An object is equal to this account if the object is an account, and its tag handler, address, password,
     * incomingServer, outgoingServer and emails are the same.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (tagHandler != null ? !tagHandler.equals(account.tagHandler) : account.tagHandler != null) return false;
        if (address != null ? !address.equals(account.address) : account.address != null) return false;
        if (password != null ? !password.equals(account.password) : account.password != null) return false;
        if (incomingServer != null ? !incomingServer.equals(account.incomingServer) : account.incomingServer != null)
            return false;
        if (outgoingServer != null ? !outgoingServer.equals(account.outgoingServer) : account.outgoingServer != null)
            return false;
        return !(emails != null ? !emails.equals(account.emails) : account.emails != null);

    }

    /**
     * {@inheritDoc}
     *
     * A hash code is built using the tag handler, address, password, incoming server, outgoing server and emails.
     */
    @Override
    public int hashCode() {
        int result = tagHandler != null ? tagHandler.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (incomingServer != null ? incomingServer.hashCode() : 0);
        result = 31 * result + (outgoingServer != null ? outgoingServer.hashCode() : 0);
        result = 31 * result + (emails != null ? emails.hashCode() : 0);
        return result;
    }


}
