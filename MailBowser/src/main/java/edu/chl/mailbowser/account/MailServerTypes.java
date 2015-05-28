package edu.chl.mailbowser.account;


/**
 * Created by mats on 16/04/15.
 *
 * A factory class for creating pre-configured AbstractMailServer objects.
 */
public enum MailServerTypes {
    GMAIL ("Gmail") {
        @Override
        public IIncomingServer createIncomingServer() {
            return new IncomingServer("imap.gmail.com", "993");
        }

        @Override
        public IOutgoingServer createOutgoingServer() {
            return new OutgoingServer("smtp.gmail.com", "587");
        }
    },
    HOTMAIL ("Hotmail") {
        @Override
        public IIncomingServer createIncomingServer() {
            return new IncomingServer("imap-mail.outlook.com", "25");
        }

        @Override
        public IOutgoingServer createOutgoingServer() {
            return new OutgoingServer("smtp.gmail.com", "587");
        }
    },
    YAHOO ("Yahoo") {
        @Override
        public IIncomingServer createIncomingServer() {
            return new IncomingServer("imap.mail.yahoo.com", "993");
        }

        @Override
        public IOutgoingServer createOutgoingServer() {
            return new OutgoingServer("smtp.mail.yahoo.com", "587");
        }
    };

    private final String name;

    MailServerTypes(String name) {
        this.name = name;
    }

    /**
     * Creates a new pre-configured mail server for fetching email.
     *
     * @return the created mail server
     */
    public abstract IIncomingServer createIncomingServer();

    /**
     * Creates a new pre-configured mail server for sending email.
     *
     * @return the created mail server
     */
    public abstract IOutgoingServer createOutgoingServer();

    @Override
    public String toString() {
        return name;
    }
}
