package edu.chl.mailbowser.main;

import edu.chl.mailbowser.account.IAccountHandler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jesper on 2015-05-07.
 *
 * A timer task that inits fetching from all accounts in the account handler.
 */

public class FetchTask extends TimerTask {
    private IAccountHandler accountHandler;

    public FetchTask(IAccountHandler accountHandler) {
        super();
        this.accountHandler = accountHandler;
    }

    /**
     * {@inheritDoc}
     *
     * This action simply initiates fetching from all the accounts in the account handler.
     */
    @Override
    public void run() {
        System.out.println("FetchTask: run()");
        accountHandler.initFetchingFromAllAccounts();
    }
}
