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
    private IAccount account;
    private ArrayList<IAccount> accounts = new ArrayList<>();

    /**
     * Sets the account.
     *
     * @param account the account to use
     */
    @Override
    public void setAccount(IAccount account) {
        this.account = account;
    }

    /**
     * Returns the account
     *
     * @return the account
     */
    @Override
    public IAccount getAccount() {
        return account;
    }

    @Override
    public List<IAccount> getAccounts() {
        return accounts;
    }

    @Override
    public List<IEmail> getAllEmails() {
        List<IEmail> emails = new ArrayList<IEmail>();

        for(IAccount account : accounts) {
            emails.addAll(account.getEmails());
        }

        return emails;
    }

    /**
     * Reads an account from disk.
     *
     * @return false if no account is found, otherwise true
     */
    @Override
    public boolean readAccount(String filename) {
        IObjectReader<IAccount> objectReader = new ObjectReader<>();

        try {
            account = objectReader.read(filename);
        } catch (ObjectReadException e) {
            return false;
        }

        return true;
    }

    /**
     * Reads a list of accounts from disk.
     *
     * @return false if no account is found, otherwise true
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
     * Writes the account to disk.
     *
     * @return true if the write was successful, otherwise false
     */
    @Override
    public boolean writeAccount(String filename) {
        IObjectWriter<IAccount> objectReaderWriter = new ObjectWriter<>();
        return objectReaderWriter.write(account, filename);
    }

    /**
     * Writes the list of accounts to disk.
     *
     * @return true if the write was successful, otherwise false
     */
    @Override
    public boolean writeAccounts(String filename) {
        IObjectWriter<ArrayList<IAccount>> objectReaderWriter = new ObjectWriter<>();
        return objectReaderWriter.write(accounts, filename);
    }

}
