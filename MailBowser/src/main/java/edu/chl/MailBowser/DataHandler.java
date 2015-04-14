package edu.chl.MailBowser;

import edu.chl.MailBowser.models.IAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mats on 14/04/15.
 */
public class DataHandler extends Observable {
    private static DataHandler instance = new DataHandler();

    private List<IAccount> accounts = new ArrayList<>();

    private DataHandler() {}

    public static DataHandler getInstance() {
        return instance;
    }

    public void addAccount(IAccount account) {
        accounts.add(account);

        firePropertyChange("addAccount", null, null);
    }

    public List<IAccount> getAccounts() {
        return accounts;
    }
}
