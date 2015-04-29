package edu.chl.MailBowser;

import edu.chl.MailBowser.event.Event;
import edu.chl.MailBowser.event.EventBus;
import edu.chl.MailBowser.event.EventTag;
import edu.chl.MailBowser.models.IAccount;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mats on 14/04/15.
 */
public enum DataHandler {
    INSTANCE;

    private List<IAccount> accounts = new ArrayList<>();

    public void addAccount(IAccount account) {
        accounts.add(account);

        EventBus.INSTANCE.publish(new Event(EventTag.ADD_ACCOUNT, accounts));
    }

    public List<IAccount> getAccounts() {
        return accounts;
    }
}
