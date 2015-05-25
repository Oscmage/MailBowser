package edu.chl.mailbowser.account;

import edu.chl.mailbowser.account.IIncomingServer;
import edu.chl.mailbowser.account.IOutgoingServer;
import edu.chl.mailbowser.account.IncomingServer;
import edu.chl.mailbowser.account.OutgoingServer;

/**
 * Created by mats on 16/04/15.
 *
 * A factory class for creating configured MailServer objects.
 */
public enum MailServerTypes {
    GMAIL {
        @Override
        public String toString() {
            return "Gmail";
        }
    };

    /**
     * Creates a new pre-configured mail server for sending email.
     *
     * @return the created mail server
     */
    public IOutgoingServer createOutgoingServer() {
        IOutgoingServer server = null;

        switch (this) {
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
     * @return the created mail server
     */
    public IIncomingServer createIncomingServer() {
        IIncomingServer server = null;

        switch (this) {
            case GMAIL:
                server = new IncomingServer("imap.gmail.com", "993");
                break;
            default:
                break;
        }

        return server;
    }
}
