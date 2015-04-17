package edu.chl.MailBowser.factories;

import edu.chl.MailBowser.models.IMailServer;
import edu.chl.MailBowser.models.MailServer;

/**
 * Created by mats on 16/04/15.
 *
 * A factory class for creating configured MailServer objects.
 */
public class MailServerFactory {
    public enum Type {
        GMAIL
    }

    private MailServerFactory() {}

    /**
     * Creates a new pre-configured mail server for sending email.
     *
     * @param serverType the type of server to create
     * @return the created mail server
     */
    public static IMailServer createOutgoingServer(Type serverType) {
        IMailServer server = null;

        switch (serverType) {
            case GMAIL:
                server = new MailServer("smtp.gmail.com", "587");
                break;
            default:
                break;
        }

        return server;
    }

    /**
     * Creates a new pre-configured mail server for receiving email.
     *
     * @param serverType the type of server to create
     * @return the created mail server
     */
    public static IMailServer createIncomingServer(Type serverType) {
        IMailServer server = null;

        switch (serverType) {
            case GMAIL:
                server = new MailServer("imap.gmail.com", "993");
                break;
            default:
                break;
        }

        return server;
    }
}
