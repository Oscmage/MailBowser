package edu.chl.MailBowser.models;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Folder;
import javax.mail.Message;

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

    @Override
    /**
     * fetches the emails from the server given in MailServer
     *
     */
    public List<IEmail> fetch(String username, String Password) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(new File("C:\\smtp.properties")));// Not sure if a file is created or if we have to create a new one with info
            Session session = Session.getDefaultInstance(props, null);

            Store store = session.getStore("imaps");
            store.connect("smtp.gmail.com", "mailbows3r@gmail.com","VG5!qBY&#f$QCmV");

            Folder allMails = store.getDefaultFolder();
            allMails.open(Folder.READ_WRITE);
            int messageCount = allMails.getMessageCount();

            System.out.println("Total Messages:- " + messageCount);

            Message[] messages = allMails.getMessages();
            System.out.println("------------------------------");
            for (int i = 0; i < 10; i++) {
                System.out.println("Mail Subject:- " + messages[i].getSubject());
            }
            allMails.close(true);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
