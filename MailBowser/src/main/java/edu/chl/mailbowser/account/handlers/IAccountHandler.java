package edu.chl.mailbowser.account.handlers;

import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.email.models.IEmail;

import java.util.List;

/**
 * Created by OscarEvertsson on 19/05/15.
 */
public interface IAccountHandler {
    void addAccount(IAccount account);
    List<IAccount> getAccounts();
    List<IEmail> getAllEmails();
    boolean readAccounts(String filename);
    boolean writeAccounts(String filename);
}
