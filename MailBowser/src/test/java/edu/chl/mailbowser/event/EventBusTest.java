package edu.chl.mailbowser.event;

import org.junit.Test;

import static org.junit.Assert.*;

public class EventBusTest implements IObserver {
    IEvent e;
    @Test
    public void testPublish() throws Exception {
        Object o = new Object();
        EventBus.INSTANCE.register(this);
        EventBus.INSTANCE.publish(new Event(EventType.FETCH_EMAILS, o));
        assertTrue(e.getType()==EventType.FETCH_EMAILS);
        assertTrue(e.getValue()==o);
    }

    @Override
    public void onEvent(IEvent evt) {
        e = evt;
    }
}