package edu.chl.mailbowser.account.models;

import edu.chl.mailbowser.email.models.Email;
import edu.chl.mailbowser.email.models.IEmail;

import java.util.List;

/**
 * Created by jesper on 2015-05-11.
 */
public class MockInServer implements IIncomingServer {
    public boolean called = false;

    @Override
    public void fetch(String username, String Password, boolean cleanFetch, Callback<List<IEmail>> callback) {
        called = true;
    }
}
