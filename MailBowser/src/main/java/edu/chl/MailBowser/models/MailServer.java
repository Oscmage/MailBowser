package edu.chl.MailBowser.models;

import javax.mail.*;
import java.util.Properties;

/**
 * Created by filip on 09/04/15.
 */
public class MailServer implements IMailServer{
    private String hostname;
    private String port;

    /**
     * Creates a new MailServer with a hostname and a port.
     *
     * @param hostname eg. smtp.gmail.com
     * @param port eg. 587
     */
    public MailServer(String hostname, String port) {
        this.hostname = hostname;
        this.port = port;
    }

    /**
     * Sends and email using the credentials from an account.
     *
     * @param email
     * @param account
     * @return true if the email was sent, otherwise false
     */
    @Override
    public boolean send(IEmail email, IAccount account){

        // Set credentials for authentication
        final String username = account.getUsername();
        final String password = account.getPassword();

        // Set server properties for the mail session
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", hostname);
        props.put("mail.smtp.port", port);

        // Set the sender of the email
        email.setSender(account.getAddress());

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
            return true;

        } catch (MessagingException e) {

            // Return false if it failed
            System.out.println("An unspecified error occured");
            return false;

        }

    }
}
