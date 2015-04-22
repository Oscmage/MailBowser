package edu.chl.MailBowser.models;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.Session;

/**
 * Created by jesper on 2015-04-22.
 *
 * A concrete implementation of IOutgoingServer.
 */
public class OutgoingServer extends MailServer implements IOutgoingServer {

    /**
     * Creates a new OutgoingServer with the specified hostname and port.
     *
     * @param hostname
     * @param port
     */
    public OutgoingServer(String hostname, String port) {
        super(hostname, port);
    }

    /**
     * Sends an email using the supplied username and password.
     *
     * @param email the email to send
     * @param username the username to authenticate with
     * @param password the password to authenticate with
     */
    @Override
    public void send(IEmail email, String username, String password) {
        // Set server properties for the mail session
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", getHostname());
        props.put("mail.smtp.port", getPort());

        // Create a new session with the specified credentials
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Try to send the mail
            Transport.send(email.getJavaxMessage(session));
            System.out.println("Email sent");
        } catch (MessagingException e) {
            System.out.println("An unspecified error occured");
        }
    }
}
