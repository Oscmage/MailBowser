package edu.chl.mailbowser.event;

/**
 * Created by mats on 29/04/15.
 *
 * An interface for objects that are observable.
 */
public interface IObservable {
    /**
     * Registers an observer to this observable.
     *
     * @param observer the observer to add
     */
    void register(IObserver observer);

    /**
     * Unregisters an observer from this observable.
     *
     * @param observer the observer to remove
     */
    void unregister(IObserver observer);

    /**
     * Publishes an event to all registered observers.
     *
     * @param evt the event to publish
     */
    void publish(IEvent evt);
}
