package edu.chl.MailBowser;

import edu.chl.MailBowser.models.IAccount;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mats on 14/04/15.
 */
public class DataHandler {
    private static DataHandler instance = new DataHandler();
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private List<IAccount> accounts = new ArrayList<>();

    private DataHandler() {}

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public static DataHandler getInstance() {
        return instance;
    }

    public void addAccount(IAccount account) {
        accounts.add(account);

        pcs.firePropertyChange("addAccount", null, null);
    }

    public List<IAccount> getAccounts() {
        return accounts;
    }
}
