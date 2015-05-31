package edu.chl.mailbowser.account;

import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.tag.ITagHandler;

import java.util.List;
import java.util.Set;

/**
 * Created by OscarEvertsson on 19/05/15.
 *
 * An interface for classes that manage accounts. This interface provides methods to add and remove accounts, and also
 * to initiate fetching in all added accounts.
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
    Set<IEmail> getAllEmails();

    /**
     * Initiates fetching from all accounts
     */
    void initFetchingFromAllAccounts();

    /**
     * Initiates fetching from all accounts
     */
    void initRefetchingFromAllAccounts();

    /**
     * Reads a file with IAccounts from disk and adds them to this account handler.
     *
     * @param filename the file to look for accounts in
     * @return false if no accounts are found, otherwise true
     */
    boolean readAccounts(String filename, ITagHandler tagHandler);

    /**
     * Writes all accounts that have been added to this account handler to a file on disk.
     *
     * @param filename the file to write the accounts to
     * @return true if the write was successful, otherwise false
     */
    boolean writeAccounts(String filename);
}
