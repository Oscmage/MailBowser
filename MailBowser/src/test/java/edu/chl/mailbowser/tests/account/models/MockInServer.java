package edu.chl.mailbowser.tests.account.models;

import edu.chl.mailbowser.account.models.Callback;
import edu.chl.mailbowser.account.models.IIncomingServer;
import edu.chl.mailbowser.account.models.Pair;
import edu.chl.mailbowser.email.models.IEmail;

/**
 * Created by jesper on 2015-05-11.
 */
public class MockInServer implements IIncomingServer {
    public boolean called = false;

    @Override
    public void fetch(String username, String Password, boolean cleanFetch, Callback<Pair<IEmail, String>> callback) {
        called = true;
    }

    @Override
    public boolean testConnection(String username, String password) {
        return false;
    }

    @Override
    public String getHostname() {
        return null;
    }

    @Override
    public String getPort() {
        return null;
    }
}
