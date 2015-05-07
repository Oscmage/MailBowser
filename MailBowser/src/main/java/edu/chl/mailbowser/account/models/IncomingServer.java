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

    private Fetcher fetcher = null;

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
    public void fetch(String username, String password) {
        if (fetcher == null) {
            fetcher = new Fetcher(username, password, new FetchCallback());
            new Thread(fetcher).start();
        }
    }

    private class FetchCallback implements Callback<List<IEmail>> {

        @Override
        public void onSuccess(List<IEmail> object) {
            EventBus.INSTANCE.publish(new Event(EventType.FETCH_EMAILS, object));
            fetcher = null;
        }

        @Override
        public void onFailure(String msg) {
            EventBus.INSTANCE.publish(new Event(EventType.FETCH_EMAILS_FAIL, null));
            fetcher = null;
        }
    }

    private class Fetcher implements Runnable {
        private Callback<List<IEmail>> callback;
        private String username;
        private String password;

        public Fetcher(String username, String password, Callback<List<IEmail>> callback) {
            this.username = username;
            this.password = password;
            this.callback = callback;
        }

        @Override
        public void run() {
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
                callback.onFailure("Failed to fetch email from server");
            }

            callback.onSuccess(emails);
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
}