package edu.chl.mailbowser.account.handlers;

import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.io.ObjectReadException;
import edu.chl.mailbowser.io.ObjectReader;
import edu.chl.mailbowser.io.ObjectWriter;

/**
 * Created by mats on 11/05/15.
 */
public class AccountHandler {
    private static final AccountHandler instance = new AccountHandler();

    private IAccount account;

    private AccountHandler() {} // Private constructor to prevent instantiation

    /**
     * Returns the singleton instance.
     *
     * @return the singleton instance
     */
    public static AccountHandler getInstance() {
        return instance;
    }

    /**
     * Sets the account.
     *
     * @param account the account to use
     */
    public void setAccount(IAccount account) {
        this.account = account;
    }

    /**
     * Returns the account
     *
     * @return the account
     */
    public IAccount getAccount() {
        return account;
    }

    /**
     * Reads an account from disk.
     *
     * @return false if no account is found, otherwise true
     */
    public boolean readAccount(String filename) {
        ObjectReader<IAccount> objectReader = new ObjectReader<>();

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
    public boolean writeAccount(String filename) {
        ObjectWriter<IAccount> objectReaderWriter = new ObjectWriter<>();
        return objectReaderWriter.write(account, filename);
    }

}
