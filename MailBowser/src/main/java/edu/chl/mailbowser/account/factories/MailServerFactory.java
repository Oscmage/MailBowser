package edu.chl.mailbowser.account.factories;

import edu.chl.mailbowser.account.models.IIncomingServer;
import edu.chl.mailbowser.account.models.IOutgoingServer;
import edu.chl.mailbowser.account.models.IncomingServer;
import edu.chl.mailbowser.account.models.OutgoingServer;

/**
 * Created by mats on 16/04/15.
 *
 * A factory class for creating configured MailServer objects.
 */
public class MailServerFactory {

    /**
     * Types of pre-defined mail servers.
     */
    public enum Type {
        GMAIL
    }

    /*
     * We define a private constructor to prevent other classes from creating objects of this class.
     */
    private MailServerFactory() {}

    /**
     * Creates a new pre-configured mail server for sending email.
     *
     * @param serverType the type of server to create
     * @return the created mail server
     */
    public static IOutgoingServer createOutgoingServer(Type serverType) {
        IOutgoingServer server = null;

        switch (serverType) {
            case GMAIL:
                server = new OutgoingServer("smtp.gmail.com", "587");
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
    public static IIncomingServer createIncomingServer(Type serverType) {
        IIncomingServer server = null;

        switch (serverType) {
            case GMAIL:
                server = new IncomingServer("imap.gmail.com", "993");
                break;
            default:
                break;
        }

        return server;
    }
}
