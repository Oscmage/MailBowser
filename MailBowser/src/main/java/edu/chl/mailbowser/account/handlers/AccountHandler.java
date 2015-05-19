package edu.chl.mailbowser.account.handlers;

import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.io.*;

/**
 * Created by mats on 11/05/15.
 *
 * A singleton for managing accounts.
 */
public class AccountHandler implements IAccountHandler{
    private IAccount account;

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
     * Writes the account to disk.
     *
     * @return true if the write was successful, otherwise false
     */
    @Override
    public boolean writeAccount(String filename) {
        IObjectWriter<IAccount> objectReaderWriter = new ObjectWriter<>();
        return objectReaderWriter.write(account, filename);
    }

}
