package edu.chl.MailBowser.models;


import java.util.Properties;
import javax.mail.Transport;
import javax.mail.Session;

/**
 * Created by jesper on 2015-04-22.
 */
public class OutgoingServer extends MailServer implements IOutgoingServer {

    @Override
    public void send(IEmail email, String username, String password) {



        // Set server properties for the mail session
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", super.getHostname);
        props.put("mail.smtp.port", super.getPort);

        // Set the sender of the email
        //email.setSender(account.getAddress()); where do we get Address without account

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
            //return true;

        } catch (MessagingException e) {

            // Return false if it failed
            System.out.println("An unspecified error occured");
            //return false;

        }
    }
}
