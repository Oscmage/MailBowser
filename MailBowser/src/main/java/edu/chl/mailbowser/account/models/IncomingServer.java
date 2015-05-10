package edu.chl.mailbowser.account.models;

import edu.chl.mailbowser.email.models.Email;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.*;

/**
 * Created by jesper on 2015-04-21.
 *
 * A concrete implementation of IIncomingServer.
 */
public class IncomingServer extends MailServer implements IIncomingServer {

    /**
     * Creates a new IncomingServer with the specified hostname and port.
     *
     * @param hostname
     * @param port
     */
    public IncomingServer(String hostname, String port) {
        super(hostname, port);
    }

    /**
     * Fetches all emails from the server, using the supplied username and password.
     *
     * @param username the username to authenticate with
     * @param password the password to authenticate with
     */
    @Override
    public List<IEmail> fetch(String username, String password) {
        List<IEmail> emails = new ArrayList<>();

        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");

        Session session = Session.getInstance(props, null);

        try {
            Store store = session.getStore();
            store.connect(getHostname(), username, password);

            // start by getting the default (root) folder, and recursively work through all subfolders
            Folder root = store.getDefaultFolder();
            emails = recursiveFetch(root);

            store.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        EventBus.INSTANCE.publish(new Event(EventType.FETCH_EMAILS, emails));
        return emails;
    }

    private List<IEmail> recursiveFetch (Folder folder) throws MessagingException {
        List<IEmail> emails = new ArrayList<>();

        if ((folder.getType() & Folder.HOLDS_MESSAGES) == Folder.HOLDS_MESSAGES) {
            folder.open(Folder.READ_ONLY);
            Message [] messages = folder.getMessages();
            for (Message message : messages) {
                emails.add(new Email(message));
            }
        }

        if ((folder.getType() & Folder.HOLDS_FOLDERS) == Folder.HOLDS_FOLDERS){
            Folder [] folders = folder.list();
            for (Folder subFolder : folders){
                emails.addAll(recursiveFetch(subFolder));
            }
        }

        return emails;
    }
}
