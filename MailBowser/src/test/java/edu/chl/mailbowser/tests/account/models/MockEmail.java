package edu.chl.mailbowser.tests.account.models;

import edu.chl.mailbowser.email.models.IAddress;
import edu.chl.mailbowser.email.models.IEmail;

import javax.mail.Message;
import javax.mail.Session;
import java.util.Date;
import java.util.List;

/**
 * Created by jesper on 2015-05-12.
 */
public class MockEmail implements IEmail {
    public boolean setSender = false;

    @Override
    public Message getJavaxMessage(Session session) {
        return null;
    }

    @Override
    public IAddress getSender() {
        return null;
    }

    @Override
    public List<IAddress> getReceivers() {
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
    public void setContent(String content) {

    }


    @Override
    public void setReceivers(List<IAddress> receivers) {

    }

    @Override
    public void setSender(IAddress sender) {
        setSender = true;
    }

    @Override
    public void setSubject(String subject) {

    }

    @Override
    public void setSent() {

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
