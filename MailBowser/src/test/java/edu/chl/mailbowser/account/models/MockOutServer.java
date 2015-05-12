package edu.chl.mailbowser.account.models;

import edu.chl.mailbowser.email.models.IEmail;

/**
 * Created by jesper on 2015-05-11.
 */
public class MockOutServer implements IOutgoingServer {
    public boolean called = false;

    @Override
    public void send(IEmail email, String username, String password, Callback<IEmail> callback) {
        called=true;
    }
}
