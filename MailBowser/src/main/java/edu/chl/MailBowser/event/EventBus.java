package edu.chl.MailBowser.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mats on 29/04/15.
 */
public enum EventBus implements IObservable {
    INSTANCE;

    // determines whether or not to print all events to the console
    private static final boolean TRACE = true;

    private final List<IObserver> observers = new ArrayList<>();

    /**
     * Registers an observer. When an event occurs the onEvent method will be called on the observer
     *
     * @param observer the observer to register
     */
    @Override
    public void register(IObserver observer) {
        observers.add(observer);
    }

    /**
     * Unregisteres an observer. The observer will no longer receive any events from the EventBus
     *
     * @param observer the observer to unregister
     */
    @Override
    public void unregister(IObserver observer) {
        observers.remove(observer);
    }

    /**
     * Publishes an event. The onEvent method will be called on all registered observers with the event as a parameter.
     *
     * @param evt the event to publish
     */
    @Override
    public void publish(IEvent evt) {
        if (TRACE) {
            System.out.println(evt);
        }

        for (IObserver observer : observers) {
            observer.onEvent(evt);
        }
    }
}
