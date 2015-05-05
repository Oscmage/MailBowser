package edu.chl.mailbowser.event;

/**
 * Created by mats on 29/04/15.
 */
public interface IObservable {
    void register(IObserver observer);
    void unregister(IObserver observer);
    void publish(IEvent evt);
}
