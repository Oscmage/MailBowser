package edu.chl.mailbowser.tests.mock;

import edu.chl.mailbowser.email.IAddress;
import edu.chl.mailbowser.email.IEmail;

import javax.mail.Message;
import javax.mail.Session;
import java.util.Date;
import java.util.List;

/**
 * Created by jesper on 2015-05-12.
 */
public class MockEmail implements IEmail {
    public boolean setSenderCalled = false;

    @Override
    public Message getJavaMailMessage(Session session) {
        return null;
    }

    @Override
    public IAddress getSender() {
        return null;
    }

    @Override
    public List<IAddress> getTo() {
        return null;
    }

    @Override
    public List<IAddress> getCc() {
        return null;
    }

    @Override
    public List<IAddress> getBcc() {
        return null;
    }

    @Override
    public List<IAddress> getAllRecipients() {
        return null;
    }

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public String getSubject() {
        return null;
    }

    @Override
    public Date getSentDate() {
        return null;
    }

    @Override
    public Date getReceivedDate() {
        return null;
    }

    @Override
    public void setSender(IAddress sender) {
        setSenderCalled = true;
    }

    @Override
    public boolean matches(String query) {
        return false;
    }

    @Override
    public int compareTo(IEmail o) {
        return 0;
    }
}
