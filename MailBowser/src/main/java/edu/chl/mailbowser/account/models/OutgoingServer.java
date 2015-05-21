package edu.chl.mailbowser.account.models;

import edu.chl.mailbowser.email.models.IEmail;

import java.util.Properties;
import javax.mail.*;

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
    public void send(IEmail email, String username, String password, Callback<IEmail> callback) {
        Sender sender = new Sender(email, username, password, callback);
        new Thread(sender).start();
    }

    private class Sender implements Runnable {
        private IEmail email;
        private String username;
        private String password;
        private Callback<IEmail> callback;

        private Sender(IEmail email, String username, String password, Callback<IEmail> callback) {
            this.email = email;
            this.username = username;
            this.password = password;
            this.callback = callback;
        }

        @Override
        public void run() {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", getHostname());
            props.put("mail.smtp.port", getPort());

            Session session = Session.getInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {
                // Try to send the mail
                Transport.send(email.getJavaMailMessage(session));
                callback.onSuccess(email);
            } catch (MessagingException e) {
                callback.onFailure("Error sending email");
            }
        }
    }
}
