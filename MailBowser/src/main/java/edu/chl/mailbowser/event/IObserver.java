package edu.chl.mailbowser.event;

/**
 * Created by mats on 29/04/15.
 */
public interface IObserver {
    /**
     * This method gets called whenever something happens in an observable that this observer listens to.
     *
     * @param evt the event that is sent by the observable
     */
    void onEvent(IEvent evt);
}
