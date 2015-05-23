package edu.chl.mailbowser.tests.event;

import edu.chl.mailbowser.event.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventBusTest implements IObserver {
    IEvent e;
    @Test
    public void testPublish() throws Exception {
        Object o = new Object();
        EventBus.INSTANCE.register(this); //Register as listener to the EventBus
        EventBus.INSTANCE.publish(new Event(EventType.FETCH_EMAILS, o)); //Publishes a event
        assertTrue(e.getType()==EventType.FETCH_EMAILS); //Same event type as sent?
        assertTrue(e.getValue()==o); //Same value as sent?
    }

    @Override
    public void onEvent(IEvent evt) { //Method to receive events that gets published
        e = evt;
    }
}