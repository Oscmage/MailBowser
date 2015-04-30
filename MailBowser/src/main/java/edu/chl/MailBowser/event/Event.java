package edu.chl.MailBowser.event;

/**
 * Created by mats on 29/04/15.
 */
public class Event implements IEvent {
    private final EventTag tag;
    private final Object value;

    /**
     * Creates a new Event with a specific tag and value.
     *
     * @param tag
     * @param value
     */
    public Event(EventTag tag, Object value) {
        this.tag = tag;
        this.value = value;
    }

    /**
     * Returns the tag of this event.
     *
     * @return the tag of this event.
     */
    @Override
    public EventTag getTag() {
        return tag;
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
        return "Event [tag=" + tag + ", value=(" + value + ")]";
    }
}
