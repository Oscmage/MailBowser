package edu.chl.mailbowser.account;

import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.utils.Callback;

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
     * {@inheritDoc}
     */
    @Override
    public void send(IEmail email, String username, String password, Callback<IEmail> callback) {
        // create a new sender object with the all the info, and start it in a new thread to prevent GUI lockups
        Sender sender = new Sender(email, username, password, callback);
        new Thread(sender).start();
    }

    /**
     * A class for sending emails asynchronously.
     */
    private class Sender implements Runnable {
        private IEmail email;
        private String username;
        private String password;

        private Callback<IEmail> callback;

        /**
         * Creates a new sender, but doesn't start it. To start the sending use <code>new Thread(sender).start()</code>.
         *
         * @param email the email to send
         * @param username the username to use when connecting to the mail server
         * @param password the password to use when connecting to the mail server
         * @param callback the callback to use when the sending is done
         */
        private Sender(IEmail email, String username, String password, Callback<IEmail> callback) {
            this.email = email;
            this.username = username;
            this.password = password;
            this.callback = callback;
        }

        /**
         * Starts the sending process.
         */
        @Override
        public void run() {
            // create a properties object, fill it with information, and use it to open a session to the mail server
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

            // try to send the email
            try {
                Transport.send(email.getJavaMailMessage(session));
                callback.onSuccess(email);
            } catch (MessagingException e) {
                e.printStackTrace();
                callback.onFailure("Error sending email");
            }
        }
    }
}
