package edu.chl.mailbowser.account;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.account.models.IAccount;

import java.util.List;

/**
 * Created by jesper on 2015-05-07.
 */
public class BackgroundFetching extends Thread {
    private static BackgroundFetching instance = new BackgroundFetching();

    private List<IAccount> accounts = MainHandler.INSTANCE.getAccountHandler().getAccounts();

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
            for(IAccount account : accounts) {
                account.fetch();
            }
            try {
                this.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
