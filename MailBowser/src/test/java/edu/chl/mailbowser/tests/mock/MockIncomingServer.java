package edu.chl.mailbowser.tests.mock;

import edu.chl.mailbowser.utils.Callback;
import edu.chl.mailbowser.account.IIncomingServer;
import edu.chl.mailbowser.utils.Pair;
import edu.chl.mailbowser.email.IEmail;

/**
 * Created by jesper on 2015-05-11.
 */
public class MockIncomingServer implements IIncomingServer {
    public boolean fetchCalled = false;
    public boolean testConnectionCalled = false;

    @Override
    public void fetch(String username, String Password, boolean cleanFetch, Callback<Pair<IEmail, String>> callback) {
        fetchCalled = true;
    }

    @Override
    public boolean testConnection(String username, String password) {
        testConnectionCalled = true;
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
