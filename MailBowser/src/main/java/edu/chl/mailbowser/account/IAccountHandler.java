package edu.chl.mailbowser.account;

import edu.chl.mailbowser.email.IEmail;

import java.util.List;

/**
 * Created by OscarEvertsson on 19/05/15.
 *
 * An interface for classes that manage accounts.
 */
public interface IAccountHandler {
    /**
     * Adds an account to the account handler.
     *
     * @param account the account to add
     */
    void addAccount(IAccount account);

    /**
     * Removes an account to the account handler.
     *
     * @param account the account to remove
     */
    void removeAccount(IAccount account);

    /**
     * Returns all the accounts that have been added to this account handler.
     *
     * @return a list of accounts
     */
    List<IAccount> getAccounts();

    /**
     * Returns a list consisting of all the emails from all the added accounts.
     *
     * @return a list of emails
     */
    List<IEmail> getAllEmails();

    /**
     * Initiates fetching from all accounts
     */
    void initFetchingFromAllAccounts();

    /**
     * Initiates fetching from all accounts
     */
    void initRefetchingFromAllAccounts();

    /**
     * Reads a file from disk with IAccounts and adds them to this account handler.
     *
     * @param filename the file to look for accounts in
     * @return false if no accounts are found, otherwise true
     */
    boolean readAccounts(String filename);

    /**
     * Writes all accounts that have been added to this account handler to a file on disk.
     *
     * @param filename the file to write the accounts to
     * @return true if the write was successful, otherwise false
     */
    boolean writeAccounts(String filename);
}
