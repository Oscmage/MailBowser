package edu.chl.mailbowser.account.handlers;

import edu.chl.mailbowser.account.factories.MailServerFactory;
import edu.chl.mailbowser.account.models.Account;
import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.email.models.Address;

import java.io.*;

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
        FileInputStream f_in_Account;
        ObjectInputStream o_in_Account;

        try {
            f_in_Account = new FileInputStream(filename);
            o_in_Account = new ObjectInputStream(f_in_Account);
            account = (Account) o_in_Account.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Writes the account to disk.
     *
     * @return true if the write was successful, otherwise false
     */
    public void writeAccount(String filename) {
        FileOutputStream f_out_Account;
        ObjectOutputStream o_out_Account;

        try {
            f_out_Account = new FileOutputStream(filename);
            o_out_Account = new ObjectOutputStream(f_out_Account);

            o_out_Account.writeObject(account);

            f_out_Account.close();
            o_out_Account.close();

            System.out.println("write: wrote Account.ser");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("write: failed writing Account.ser");
        }
    }
}
