package edu.chl.mailbowser.account.handlers;

import edu.chl.mailbowser.account.models.Account;
import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.io.*;
import edu.chl.mailbowser.tag.handlers.ITagHandler;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mats on 11/05/15.
 *
 * A concrete implementation of IAccountHandler. This particular implementation stores the accounts in a list.
 */
public class AccountHandler implements IAccountHandler{
    private List<IAccount> accounts = new ArrayList<>();


    /**
     * {@inheritDoc}
     */
    @Override
    public void addAccount(IAccount account) {
        accounts.add(account);
        EventBus.INSTANCE.publish(new Event(EventType.ADD_ACCOUNT, account));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAccount(IAccount account) {
        accounts.remove(account);
        EventBus.INSTANCE.publish(new Event(EventType.REMOVE_ACCOUNT, account));
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
    public List<IEmail> getAllEmails() {
        List<IEmail> emails = new ArrayList<IEmail>();

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
        return objectReaderWriter.write((ArrayList<IAccount>) accounts, filename);
    }
}
