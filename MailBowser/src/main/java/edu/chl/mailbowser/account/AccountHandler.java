package edu.chl.mailbowser.account;

import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.utils.io.*;
import edu.chl.mailbowser.tag.ITagHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by mats on 11/05/15.
 *
 * A concrete implementation of IAccountHandler. This particular implementation stores the accounts in a list.
 */
public class AccountHandler implements IAccountHandler {
    // a list of all accounts that have been added to this handler
    private ArrayList<IAccount> accounts = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAccount(IAccount account) {
        accounts.add(account);
        EventBus.INSTANCE.publish(new Event(EventType.ACCOUNT_ADDED, account));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAccount(IAccount account) {
        accounts.remove(account);
        EventBus.INSTANCE.publish(new Event(EventType.ACCOUNT_REMOVED, account));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IAccount> getAccounts() {
        return new ArrayList<>(accounts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<IEmail> getAllEmails() {
        Set<IEmail> emails = new TreeSet<>();

        for(IAccount account : accounts) {
            emails.addAll(account.getEmails());
        }

        return emails;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initFetchingFromAllAccounts() {
        accounts.forEach(IAccount::fetch);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initRefetchingFromAllAccounts() {
        accounts.forEach(IAccount::refetch);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean readAccounts(String filename, ITagHandler tagHandler) {
        IObjectReader<ArrayList<IAccount>> objectReader = new ObjectReader<>();

        try {
            accounts = objectReader.read(filename);

            // the tag handler is not saved to disk with the IAccount, so here we set it manually
            for (IAccount account : accounts) {
                account.setTagHandler(tagHandler);
            }
        } catch (ObjectReadException e) {
            // initiate accounts to a new empty ArrayList to make sure that no corrupt data has been loaded
            accounts = new ArrayList<>();
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeAccounts(String filename) {
        IObjectWriter<ArrayList<IAccount>> objectReaderWriter = new ObjectWriter<>();
        return objectReaderWriter.write(accounts, filename);
    }
}
