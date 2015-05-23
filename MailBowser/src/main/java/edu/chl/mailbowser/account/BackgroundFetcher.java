package edu.chl.mailbowser.account;

import edu.chl.mailbowser.account.handlers.IAccountHandler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jesper on 2015-05-07.
 *
 * A concrete implementation of IBackgroundFetcher.
 */
public class BackgroundFetcher implements IBackgroundFetcher {
    private final int FETCH_INTERVAL = 30000;

    private IAccountHandler accountHandler;
    private Timer timer = new Timer(true);

    /**
     * Creates a new background fetcher with a specified account handler.
     * On every timer interval initFetchingFromAllAccounts() gets called on the supplied account handler.
     *
     * @param accountHandler the account handler to use
     */
    public BackgroundFetcher(IAccountHandler accountHandler) {
        this.accountHandler = accountHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        System.out.println("BackgroundFetcher: start()");
        timer.schedule(new FetchTask(), 0, FETCH_INTERVAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        System.out.println("BackgroundFetcher: stop()");
        timer.cancel();
    }

    /**
     * A timer task that inits fetching from all accounts in the account handler.
     */
    private class FetchTask extends TimerTask {
        /**
         * {@inheritDoc}
         *
         * This action simply initiates fetching from all the accounts in the account handler.
         */
        @Override
        public void run() {
            System.out.println("BackgroundFetcher: FetchTask: run()");
            accountHandler.initFetchingFromAllAccounts();
        }
    }
}
