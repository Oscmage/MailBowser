package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.email.models.Email;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;

/**
 * Created by filip on 04/05/15.
 */
public class EmailDetailPresenter implements IObserver{


    public EmailDetailPresenter() {
        EventBus.INSTANCE.register(this);
    }

    @Override
    public void onEvent(IEvent evt) {
        if(evt.getType() == EventType.SELECTED_EMAIL) {
            updateView((Email)evt.getValue());
        }
    }

    private void updateView(IEmail email){

    }
}

