package edu.chl.mailbowser.account.models;

import edu.chl.mailbowser.email.models.Email;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.tag.handlers.TagHandler;
import edu.chl.mailbowser.tag.models.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.search.FlagTerm;

/**
 * Created by jesper on 2015-04-21.
 *
 * A concrete implementation of IIncomingServer.
 */
public class IncomingServer extends MailServer implements IIncomingServer {

    private Flags processedFlag = new Flags("MailBowserProcessed");

    private transient Fetcher fetcher = null;

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
    public void fetch(String username, String password, boolean cleanFetch, Callback<List<IEmail>> callback) {
        if (fetcher == null) {
            fetcher = new Fetcher(username, password, cleanFetch, new Callback<List<IEmail>>() {
                @Override
                public void onSuccess(List<IEmail> object) {
                    fetcher = null;
                    callback.onSuccess(object);
                }

                @Override
                public void onFailure(String msg) {
                    fetcher = null;
                    callback.onFailure(msg);
                }
            });
            new Thread(fetcher).start();
        }
    }

    @Override
    public boolean testConnection(String username, String password) {
        List<IEmail> emails;

        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");

        Session session = Session.getInstance(props, null);

        try {
            Store store = session.getStore();
            store.connect(getHostname(), username, password);
        } catch (MessagingException e) {
            return false;
        }
        return true;
    }

    private class Fetcher implements Runnable {
        private Callback<List<IEmail>> callback;
        private String username;
        private String password;
        private boolean clearProcessedFlag;

        public Fetcher(String username, String password, boolean clearProcessedFlag, Callback<List<IEmail>> callback) {
            this.username = username;
            this.password = password;
            this.clearProcessedFlag = clearProcessedFlag;
            this.callback = callback;
        }

        @Override
        public void run() {
            List<IEmail> emails;

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

                callback.onSuccess(emails);
            } catch (MessagingException e) {
                System.out.println(e);
                callback.onFailure("Failed to fetch email from server");
            }
        }

        private List<IEmail> recursiveFetch (Folder folder) throws MessagingException {
            List<IEmail> emails = new ArrayList<>();

            FetchProfile fetchProfile = new FetchProfile();

            fetchProfile.add(FetchProfile.Item.ENVELOPE);
            fetchProfile.add(FetchProfile.Item.FLAGS);
            fetchProfile.add(FetchProfile.Item.CONTENT_INFO);
            fetchProfile.add("X-mailer");

            if ((folder.getType() & Folder.HOLDS_MESSAGES) == Folder.HOLDS_MESSAGES) {
                folder.open(Folder.READ_WRITE);

                if (clearProcessedFlag) {
                    Message[] messages = folder.search(new FlagTerm(processedFlag, true));
                    folder.setFlags(messages, processedFlag, false);
                }

                Message[] messages = folder.search(new FlagTerm(processedFlag, false));

                if (clearProcessedFlag) {
                    folder.setFlags(messages, processedFlag, false);
                }

                for (Message message : messages) {
                    IEmail email = new Email(message);
                    emails.add(email);

                    message.setFlags(processedFlag, true);

                    TagHandler.getInstance().addTag(email, new Tag(folder.getName()));

                    EventBus.INSTANCE.publish(new Event(EventType.FETCH_EMAIL, email));
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
