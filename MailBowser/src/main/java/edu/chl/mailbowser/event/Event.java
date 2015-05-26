package edu.chl.mailbowser.event;

/**
 * Created by mats on 29/04/15.
 *
 * A concrete implementation of the IEvent interface.
 */
public class Event implements IEvent {
    private final EventType type;
    private final Object value;

    /**
     * Creates a new Event with a specific type and value.
     *
     * @param type
     * @param value
     */
    public Event(EventType type, Object value) {
        this.type = type;
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventType getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getValue() {
        return value;
    }

    /**
     * Returns a string representation of this event. The string will look something like this: "Event [type=type, value=(value)]"
     *
     * @return a string representation of this event.
     */
    @Override
    public String toString() {
        return "Event [type=" + type + ", value=(" + value + ")]";
    }
}
