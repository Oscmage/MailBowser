package edu.chl.mailbowser.tests.mock;

import edu.chl.mailbowser.account.AccountHandler;
import edu.chl.mailbowser.account.IAccount;
import edu.chl.mailbowser.account.IIncomingServer;
import edu.chl.mailbowser.account.IOutgoingServer;
import edu.chl.mailbowser.email.IAddress;
import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.tag.ITagHandler;
import edu.chl.mailbowser.tests.account.AccountHandlerTest;

import java.util.Set;

/**
 * Created by jesper on 2015-05-31.
 */
public class MockAccount implements IAccount {
    public boolean fetchIsCalled = false;
    public boolean refetchIsCalled = false;

    @Override
    public IAddress getAddress() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public IIncomingServer getIncomingServer() {
        return null;
    }

    @Override
    public IOutgoingServer getOutgoingServer() {
        return null;
    }

    @Override
    public Set<IEmail> getEmails() {
        return null;
    }

    @Override
    public ITagHandler getTagHandler() {
        return null;
    }

    @Override
    public void setAddress(IAddress address) {

    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public void setIncomingServer(IIncomingServer server) {

    }

    @Override
    public void setOutgoingServer(IOutgoingServer server) {

    }

    @Override
    public void setTagHandler(ITagHandler tagHandler) {

    }

    @Override
    public void send(IEmail email) {

    }

    @Override
    public void fetch() {
        fetchIsCalled = true;
    }

    @Override
    public void refetch() {
        refetchIsCalled = true;
    }

    @Override
    public boolean testConnect() {
        return false;
    }
}
