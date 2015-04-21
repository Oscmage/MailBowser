package edu.chl.MailBowser.models;

import java.util.List;

/**
 * Created by jesper on 2015-04-21.
 */
public interface IIncomingServer {
    public List<IEmail> fetch(String username, String Password);
}
