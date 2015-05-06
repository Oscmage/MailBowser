package edu.chl.mailbowser.mailserver.models;

import edu.chl.mailbowser.email.models.IEmail;

import java.util.List;

/**
 * Created by jesper on 2015-04-21.
 */
public interface IIncomingServer {
    public List<IEmail> fetch(String username, String Password);
}
