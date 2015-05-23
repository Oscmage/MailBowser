package edu.chl.mailbowser.account.models;

import edu.chl.mailbowser.email.models.Email;
import edu.chl.mailbowser.email.models.IEmail;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by jesper on 2015-04-21.
 *
 * A concrete implementation of IIncomingServer.
 */
public class IncomingServer extends MailServer implements IIncomingServer {

    // this flag will be used to mark emails that have previously fetched
    private static final Flags PROCESSED_FLAG = new Flags("MailBowserProcessed");

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
     * {@inheritDoc}
     */
    @Override
    public void fetch(String username, String password, boolean cleanFetch, Callback<Pair<IEmail, String>> callback) {
        System.out.println("IncomingServer: fetch(" + username + ", " + password + ", " + cleanFetch + ", " + callback);

        // this is to prevent multiple simultaneous fetch calls to the server
        // when a fetch is initiated, fetcher gets a value. when the fetch is done, fetcher is set to null
        if (fetcher == null) {
            fetcher = new Fetcher(username, password, cleanFetch, callback, new Callback<List<IEmail>>() {
                @Override
                public void onSuccess(List<IEmail> object) {
                    System.out.println("Fetcher: onSuccess(" + object + ")");
                    fetcher = null;
                }

                @Override
                public void onFailure(String msg) {
                    System.out.println("Fetcher: onFailure(" + msg + ")");
                    fetcher = null;
                    callback.onFailure(msg);
                }
            });

            // start the fetcher in a new thread to prevent GUI lockups
            new Thread(fetcher).start();
        }
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * A class for fetching emails asynchronously.
     */
    private class Fetcher implements Runnable {
        // the onSuccess method on this callback is called whenever a new email is fetched from the server
        private Callback<Pair<IEmail, String>> callback;
        // the onSuccess method on this callback is called when the fetching is done, and all emails have been fetched from the server
        private Callback<List<IEmail>> callbackDone;

        private String username;
        private String password;
        private boolean clearProcessedFlag;

        /**
         * Creates a new fetcher, but doesn't start it. To start the fetching use <code>new Thread(fetcher).start()</code>.
         *
         * @param username the username to use when connecting to the mail server
         * @param password the password to use when connecting to the mail server
         * @param clearProcessedFlag a flag indicating whether or not to clear the processed flag from the emails on the
         *                           server. If true, all emails will be fetched from the server. If false, only emails
         *                           that haven't been fetched before will be fetched.
         * @param callback the callback to use whenever a new email is fetched
         * @param callbackDone the callback to use when the fetching is done
         */
        public Fetcher(String username, String password, boolean clearProcessedFlag,
                       Callback<Pair<IEmail, String>> callback, Callback<List<IEmail>> callbackDone) {
            this.username = username;
            this.password = password;
            this.clearProcessedFlag = clearProcessedFlag;
            this.callback = callback;
            this.callbackDone = callbackDone;
        }

        /**
         * Starts the fetching process
         */
        @Override
        public void run() {
            System.out.println("Fetcher: run()");

            List<IEmail> emails;

            // create a properties object, fill it with information, and use it to open a session to the mail server
            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imaps");

            Session session = Session.getInstance(props, null);
            Store store = null;

            try {
                // get a message store from the session, and connect to it using the given username and password
                store = session.getStore();
                store.connect(getHostname(), username, password);

                // start by getting the default (root) folder from the store, and recursively work through all subfolders
                Folder root = store.getDefaultFolder();
                emails = recursiveFetch(root);

                callbackDone.onSuccess(emails);
            } catch (MessagingException e) {
                e.printStackTrace();
                callback.onFailure("Failed to fetch email from server");
                callbackDone.onFailure("Failed to fetch emails from server");
            } finally {
                if (store != null) {
                    try {
                        store.close();
                    } catch (MessagingException e) {
                        // the folder is closed even if an exception occurs, so we don't have do take any action
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * A method that works through folders recursively, and fetches the emails in each folder.
         *
         * @param folder the folder to begin with
         * @return a list of all emails contained in this folder and all of its subfolders
         * @throws MessagingException
         */
        private List<IEmail> recursiveFetch (Folder folder) {
            List<IEmail> emails = new ArrayList<>();

            // a folder can be of two types - HOLDS_MESSAGES and HOLDS_FOLDERS. depending on what type the folder is,
            // different actions are performed. a single folder can be of both types
            int folderType;
            try {
                folderType = folder.getType();
            } catch (MessagingException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }

            // if the folder holds messages, fetch them
            if ((folderType & Folder.HOLDS_MESSAGES) == Folder.HOLDS_MESSAGES) {
                try {
                    folder.open(Folder.READ_WRITE);

                    // if the clearProcessedFlag is set, search for all emails flagged with the processed flag and set it to false
                    if (clearProcessedFlag) {
                        clearProcessedFlag(folder);
                    }

                    // search for all messages where the processed flag is not set, and flag them with the processed flag
                    Message[] messages = folder.search(new FlagTerm(PROCESSED_FLAG, false));
                    folder.setFlags(messages, PROCESSED_FLAG, true);

                    // loop through all messages and create an email object for each one. tell the callback that an
                    // email has been fetched, and from what folder it was fetched
                    for (Message message : messages) {
                        IEmail email = new Email(message);
                        callback.onSuccess(new Pair<>(email, folder.getName()));

                        // add the email to the list of emails that will be returned in the final callback
                        emails.add(email);
                    }
                } catch (MessagingException e) {
                    e.printStackTrace();
                    return new ArrayList<>();
                } finally {
                    try {
                        folder.close(false);
                    } catch (MessagingException e) {
                        // the folder is closed even if an exception occurs, so we don't have do take any action
                        e.printStackTrace();
                    }
                }
            }

            // if the folder holds folders, call recursiveFetch on each subfolder
            if ((folderType & Folder.HOLDS_FOLDERS) == Folder.HOLDS_FOLDERS){
                Folder[] folders;
                try {
                    folders = folder.list();
                } catch (MessagingException e) {
                    // if something goes wrong, set folders to an empty array
                    folders = new Folder[0];
                    e.printStackTrace();
                }

                for (Folder subFolder : folders){
                    emails.addAll(recursiveFetch(subFolder));
                }
            }

            return emails;
        }

        /**
         * Finds all emails that are flagged with the processed flag and removes the flag from them.
         *
         * @param folder the folder to search in
         * @throws MessagingException when something goes wrong with the connection to the remote folder
         */
        private void clearProcessedFlag(Folder folder) throws MessagingException {
            Message[] messages = folder.search(new FlagTerm(PROCESSED_FLAG, true));
            folder.setFlags(messages, PROCESSED_FLAG, false);
        }
    }
}
