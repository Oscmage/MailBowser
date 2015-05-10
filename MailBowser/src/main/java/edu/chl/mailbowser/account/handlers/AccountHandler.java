package edu.chl.mailbowser.account.handlers;

import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.account.models.IAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mats on 14/04/15.
 */
public enum AccountHandler {
    INSTANCE;

    private List<IAccount> accounts = new ArrayList<>();

    public void addAccount(IAccount account) {
        accounts.add(account);

        EventBus.INSTANCE.publish(new Event(EventType.ADD_ACCOUNT, account));
    }

    public List<IAccount> getAccounts() {
        return accounts;
    }

    public IAccount getAccount(int index) {
        return accounts.get(index);
    }
}
