package edu.chl.mailbowser.account;

/**
 * Created by jesper on 21/05/15.
 *
 * An interface for background fetchers.
 */
public interface IBackgroundFetching {
    /**
     * Starts the background fetching process.
     */
    void start();

    /**
     * Stops the background fetching process.
     */
    void stop();
}
