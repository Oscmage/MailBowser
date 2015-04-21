package edu.chl.MailBowser.models;

import java.util.List;

/**
 * Created by jesper on 2015-04-21.
 * A concrete inplmentation of IIncomingServer.
 */
public class IncomingServer extends MailServer implements IIncomingServer {

    @Override
    /**
     * fetches the emails from th server given in MailServer
     *
     */
    public List<Email> fetch(String username, String Password) {
        return null;
    }

}
