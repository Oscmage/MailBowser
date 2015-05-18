package edu.chl.mailbowser.account;

import edu.chl.mailbowser.account.handlers.AccountHandler;
import edu.chl.mailbowser.account.models.IAccount;

/**
 * Created by jesper on 2015-05-07.
 */
public class BackgroundFetching extends Thread {
    private static BackgroundFetching instance = new BackgroundFetching();

    private IAccount account = AccountHandler.getInstance().getAccount();

    private BackgroundFetching(){}

    public static BackgroundFetching getInstance(){
        return instance;
    }

    /**
     * Tells account to fetchs emails every 30 secounds.
     */
    @Override
    public void run() {
        while (!this.isInterrupted()) {
            account.fetch();
            try {
                this.sleep(300000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
