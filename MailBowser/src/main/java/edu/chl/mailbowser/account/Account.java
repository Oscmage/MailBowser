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

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by OscarEvertsson on 09/04/15.
 *
 * A concrete implementation of the IAccount interface.
 */
public class Account implements IAccount {
    private static final long serialVersionUID = -1081064603886454171L;

    // the key to use when encrypting and decrypting the password. the password is never saved in plain text, it is
    // only saved as an encrypted byte array
    private static final String KEY = "%*tR7sfa";
    private byte[] password;

    // the address that is used when sending emails. the getString() value of this address is also used when
    // authenticating with mail servers
    private IAddress address;

    private IIncomingServer incomingServer;
    private IOutgoingServer outgoingServer;
    private transient ITagHandler tagHandler;
    private Set<IEmail> emails = new TreeSet<>();

    /**
     * Creates a new account.
     *
     * @param address the address to use when sending email from this account. The result of getString() on this IAddress
     *                is also used when authenticating with mail servers
     * @param password the password to use when authenticating with mail servers
     * @param incomingServer the server to use when fetching emails
     * @param outgoingServer the server to use when sending emails
     * @param tagHandler the tag handler to use when tagging emails
     * @throws IllegalArgumentException if any parameter is null
     */
    public Account(IAddress address, String password, IIncomingServer incomingServer, IOutgoingServer outgoingServer,
                   ITagHandler tagHandler) {
        if (address == null) {
            throw new IllegalArgumentException("Can't create an Account with address null");
        }
        if (password == null) {
            throw new IllegalArgumentException("Can't create an Account with password null");
        }
        if (incomingServer == null) {
            throw new IllegalArgumentException("Can't create an Account with incomingServer null");
        }
        if (outgoingServer == null) {
            throw new IllegalArgumentException("Can't create an Account with outgoingServer null");
        }
        if (tagHandler == null) {
            throw new IllegalArgumentException("Can't create an Account with tagHandler null");
        }

        this.address = address;
        setPassword(password);
        this.incomingServer = incomingServer;
        this.outgoingServer = outgoingServer;
        this.tagHandler = tagHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IAddress getAddress() {
        return address;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsername() {
        return address.getString();
    }

    /**
     * {@inheritDoc}
     *
     * The password is never saved in plain text. It is encrypted to a byte array in the set method, and decrypted in
     * the get method.
     */
    @Override
    public String getPassword() {
        return Crypto.decryptByteArray(password, KEY, "UTF-8");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IIncomingServer getIncomingServer() {
        return incomingServer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IOutgoingServer getOutgoingServer() {
        return outgoingServer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<IEmail> getEmails() {
        return emails;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITagHandler getTagHandler() {
        return tagHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAddress(IAddress address) {
        this.address = address;
    }

    /**
     * {@inheritDoc}
     *
     * The password is never saved in plain text. It is encrypted to a byte array in the set method, and decrypted in
     * the get method.
     *
     * This method is final because it is used in the constructor. This means that the behaviour of this method is
     * guaranteed to be the same even if the class is subclassed.
     */
    @Override
    public final void setPassword(String password) {
        this.password = Crypto.encryptString(password, KEY, "UTF-8");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIncomingServer(IIncomingServer server) {
        // TODO: make immutable?
        this.incomingServer = server;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOutgoingServer(IOutgoingServer server) {
        // TODO: make immutable?
        this.outgoingServer = server;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTagHandler(ITagHandler tagHandler) {
        this.tagHandler = tagHandler;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException when a null email is sent
     */
    @Override
    public void send(IEmail email) {
        if (email == null) {
            throw new IllegalArgumentException("Can't send a null email");
        }

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
     * {@inheritDoc}
     */
    @Override
    public void fetch() {
        System.out.println("Account: fetch()");
        initFetch(false);
    }

    /**
     * Clears the already fetched emails and does a new fetch from a clean state using the initFetch method.
     */
    @Override
    public void refetch() {
        System.out.println("Account: refetch()");
        emails = new TreeSet<>();
        EventBus.INSTANCE.publish(new Event(EventType.CLEAR_EMAILS, null));
        tagHandler.reset();
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
     */
    @Override
    public boolean testConnect(){
        return incomingServer.testConnection(getUsername(),getPassword());
    }

    /**
     * Returns a string representation of this object. The string will look something like this: "address@example.com".
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return address.toString();
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
        if (password != null ? !Arrays.equals(password, account.password) : account.password != null) return false;
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
        result = 31 * result + (password != null ? Arrays.hashCode(password) : 0);
        result = 31 * result + (incomingServer != null ? incomingServer.hashCode() : 0);
        result = 31 * result + (outgoingServer != null ? outgoingServer.hashCode() : 0);
        result = 31 * result + (emails != null ? emails.hashCode() : 0);
        return result;
    }
}
