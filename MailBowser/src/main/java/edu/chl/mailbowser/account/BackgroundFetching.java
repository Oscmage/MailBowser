package edu.chl.mailbowser.account;

import edu.chl.mailbowser.account.models.Account;

/**
 * Created by jesper on 2015-05-07.
 */
public class BackgroundFetching extends Thread {
    private static BackgroundFetching instance = new BackgroundFetching();

    private Account account = Account.INSTANCE;

    private BackgroundFetching(){}

    public static BackgroundFetching getInstance(){
        return instance;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            account.fetch();
            try {
                this.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
