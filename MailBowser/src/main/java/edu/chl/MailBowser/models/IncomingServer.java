package edu.chl.MailBowser.models;

import java.io.File;
import java.io.FileInputStream;
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
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");

        Session session = Session.getInstance(props, null);

        try {
            Store store = session.getStore();
            store.connect(getHostname(), username, password);

            Folder root = store.getFolder("INBOX");
            root.open(Folder.READ_ONLY);

            Message[] messages = root.getMessages();

            for (int i = 0; i < messages.length; i++) {
                try {
                    System.out.println(messages[i].getSubject());
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }

            root.close(true);
            store.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<IEmail> recursiveFetch (Folder folder) throws MessagingException {
        List<IEmail> emails = new ArrayList<>();
        if ((folder.getType() & Folder.HOLDS_MESSAGES) == Folder.HOLDS_MESSAGES) {
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
