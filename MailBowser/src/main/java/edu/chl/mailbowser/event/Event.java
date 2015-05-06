package edu.chl.mailbowser.event;

/**
 * Created by mats on 29/04/15.
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
     * Returns the type of this event.
     *
     * @return the type of this event.
     */
    @Override
    public EventType getType() {
        return type;
    }

    /**
     * Returns the value of this event.
     *
     * @return the value of this event.
     */
    @Override
    public Object getValue() {
        return value;
    }

    /**
     * Returns a string representation of this event.
     *
     * @return a string representation of this event.
     */
    @Override
    public String toString() {
        return "Event [type=" + type + ", value=(" + value + ")]";
    }
}
