package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * Created by filip on 04/05/15.
 */
public class SidebarPresenter implements IObserver{

    @FXML private ListView sidebarListView;

    public SidebarPresenter() {
        EventBus.INSTANCE.register(this);
    }

    @Override
    public void onEvent(IEvent evt) {

    }

    /**
     * If the current selected item is "All emails"(index 0) the method sends an event with null
     * since All emails isn't a tag.
     * Otherwise the method sends an event with the chosen tag.
     * @param event
     */
    public void onItemChanged(Event event) {
        if(sidebarListView.getSelectionModel().getSelectedIndex() != 0) {
            EventBus.INSTANCE.publish(new edu.chl.mailbowser.event.Event(EventType.SELECTED_TAG, null));
        } else {
            EventBus.INSTANCE.publish(new edu.chl.mailbowser.event.Event(EventType.SELECTED_TAG,
                    sidebarListView.getSelectionModel().getSelectedItem()));
        }
    }
}

