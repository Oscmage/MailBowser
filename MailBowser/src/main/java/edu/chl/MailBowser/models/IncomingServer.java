package edu.chl.MailBowser.models;

import java.util.List;

/**
 * Created by jesper on 2015-04-21.
 */
public class IncomingServer extends MailServer implements IIncomingServer {

    @Override
    public List<Email> fetch(String username, String Password) {
        return null;
    }

}
