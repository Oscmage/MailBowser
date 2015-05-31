package edu.chl.mailbowser.event;

/**
 * Created by mats on 29/04/15.
 *
 * An interface for an event. An event has a type and a value.
 * An IEvent is sent using {@link edu.chl.mailbowser.event.EventBus} to notify its listners
 */
public interface IEvent {
    /**
     * Returns the type of this event.
     *
     * @return the type of this event
     */
    EventType getType();

    /**
     * Returns the value of this event.
     *
     * @return the value of this event
     */
    Object getValue();
}
