package edu.chl.mailbowser.account.models;

import edu.chl.mailbowser.email.models.IEmail;

import java.util.List;

/**
 * Created by jesper on 2015-04-21.
 */
public interface IIncomingServer {
    public void fetch(String username, String Password);
}
