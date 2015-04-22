package edu.chl.MailBowser.models;

import java.util.List;

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
     * fetches the emails from th server given in MailServer
     *
     */
    public List<IEmail> fetch(String username, String Password) {
        return null;
    }

}
