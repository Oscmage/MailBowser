package edu.chl.MailBowser.event;

/**
 * Created by mats on 29/04/15.
 */
public interface IObserver {
    void onEvent(IEvent evt);
}