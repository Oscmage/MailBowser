package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
import edu.chl.mailbowser.tag.handlers.ITagHandler;
import edu.chl.mailbowser.tag.handlers.TagHandler;
import edu.chl.mailbowser.tag.models.ITag;
import edu.chl.mailbowser.tag.models.Tag;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by filip on 04/05/15.
 */
public class SidebarPresenter implements IObserver, Initializable {

    private ITagHandler tagHandler = MainHandler.INSTANCE.getTagHandler();

    @FXML private ListView<SidebarViewItemPresenter> sidebarListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.INSTANCE.register(this);
        updateView();
    }

    public void updateView() {
        Set<ITag> tags = tagHandler.getTags();

        for(ITag tag : tags) {
            updateView(tag);
        }
    }

    public void updateView(Set<ITag> tags) {
        if (!tags.isEmpty()) {
            for (ITag tag : tags) {
                updateView(tag);
            }
        }
    }

    public void updateView(ITag tag) {
        ObservableList<SidebarViewItemPresenter> observableList = sidebarListView.getItems();

        SidebarViewItemPresenter emailListViewItem = new SidebarViewItemPresenter((Tag)tag);

        if(!observableList.contains(emailListViewItem)) {
            observableList.add(emailListViewItem);
        }

        sidebarListView.setItems(observableList);
    }

    public void deleteTag(SidebarViewItemPresenter listItem) {
        tagHandler.removeTag(listItem.getTag());
        sidebarListView.getItems().remove(listItem);
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

    @Override
    public void onEvent(IEvent evt) {
        switch (evt.getType()) {
            case FETCH_EMAIL:
                Platform.runLater(
                        () -> updateView(tagHandler.getTags((IEmail)evt.getValue()))
                );
            case ADD_TAG:
                Platform.runLater(
                        () -> updateView((ITag)evt.getValue())
                );
            case DELETE_TAG:
                Platform.runLater(
                        () -> deleteTag((SidebarViewItemPresenter)evt.getValue())
                );
        }
    }

}

