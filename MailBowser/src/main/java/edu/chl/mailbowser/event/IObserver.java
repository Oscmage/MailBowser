package edu.chl.mailbowser.event;

/**
 * Created by mats on 29/04/15.
 *
 * An interface for objects that can listen to observables.
 */
public interface IObserver {
    /**
     * This method gets called whenever something happens in an observable that this observer is listening to.
     *
     * @param evt the event that is sent by the observable
     */
    void onEvent(IEvent evt);
}
