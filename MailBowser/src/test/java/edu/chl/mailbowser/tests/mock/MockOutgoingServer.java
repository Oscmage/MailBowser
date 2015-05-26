package edu.chl.mailbowser.tests.mock;

import edu.chl.mailbowser.utils.Callback;
import edu.chl.mailbowser.account.IOutgoingServer;
import edu.chl.mailbowser.email.IEmail;

/**
 * Created by jesper on 2015-05-11.
 */
public class MockOutgoingServer implements IOutgoingServer {
    public boolean sendCalled = false;

    @Override
    public void send(IEmail email, String username, String password, Callback<IEmail> callback) {
        sendCalled = true;
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
