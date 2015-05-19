package edu.chl.mailbowser.account.handlers;

import edu.chl.mailbowser.account.models.IAccount;

/**
 * Created by OscarEvertsson on 19/05/15.
 */
public interface IAccountHandler {

    void setAccount(IAccount account);
    IAccount getAccount();
    boolean readAccount(String filename);
    boolean writeAccount(String filename);
}
