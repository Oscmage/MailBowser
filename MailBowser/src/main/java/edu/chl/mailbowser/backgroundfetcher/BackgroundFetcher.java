package edu.chl.mailbowser.backgroundfetcher;

import edu.chl.mailbowser.account.IAccountHandler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jesper on 2015-05-07.
 *
 * A concrete implementation of IBackgroundFetcher. This fetcher is initiated with an interval and an account handler.
 * A timer is started with the interval, and a fetch is initiated using the account handler on every iteration.
 */
public class BackgroundFetcher implements IBackgroundFetcher {
    private int fetchInterval;
    private IAccountHandler accountHandler;

    // an instance variable to keep track of the timer. this is used for starting and stopping the timer
    private Timer timer = new Timer(true);

    /**
     * Creates a new background fetcher with a specified account handler.
     * On every timer interval initFetchingFromAllAccounts() gets called on the supplied account handler.
     *
     * @param fetchInterval the number of milliseconds to wait between each fetch
     * @param accountHandler the account handler to use
     */
    public BackgroundFetcher(int fetchInterval, IAccountHandler accountHandler) {
        this.fetchInterval = fetchInterval;
        this.accountHandler = accountHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        System.out.println("BackgroundFetcher: start()");
        timer.schedule(new FetchTask(), 0, fetchInterval);
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