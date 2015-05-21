package edu.chl.mailbowser.account.handlers;

import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.io.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mats on 11/05/15.
 *
 * A singleton for managing accounts.
 */
public class AccountHandler implements IAccountHandler{
    private ArrayList<IAccount> accounts = new ArrayList<>();

    /**
     * Adds an account to the list of accounts.
     *
     * @param account the account to add
     */
    @Override
    public void addAccount(IAccount account) {
        accounts.add(account);
    }

    /**
     * Returns a list with all the added accounts
     *
     * @return a list of accounts
     */
    @Override
    public List<IAccount> getAccounts() {
        return new ArrayList<>(accounts);
    }

    /**
     * Returns a list with the emails from all added accounts.
     *
     * @return a list of emails
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
     * Reads a list of accounts from disk.
     *
     * @return false if no accounts are found, otherwise true
     */
    @Override
    public boolean readAccounts(String filename) {
        IObjectReader<ArrayList<IAccount>> objectReader = new ObjectReader<>();

        try {
            accounts = objectReader.read(filename);
        } catch (ObjectReadException e) {
            return false;
        }

        return true;
    }

    /**
     * Writes the list of added accounts to disk.
     *
     * @return true if the write was successful, otherwise false
     */
    @Override
    public boolean writeAccounts(String filename) {
        IObjectWriter<ArrayList<IAccount>> objectReaderWriter = new ObjectWriter<>();
        return objectReaderWriter.write(accounts, filename);
    }

}
