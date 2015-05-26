package edu.chl.mailbowser.backgroundfetcher;

/**
 * Created by jesper on 21/05/15.
 *
 * An interface for background fetchers.
 */
public interface IBackgroundFetcher {
    /**
     * Starts the background fetching process.
     */
    void start();

    /**
     * Stops the background fetching process.
     */
    void stop();
}
