package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.main.MainHandler;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
import edu.chl.mailbowser.event.*;
import edu.chl.mailbowser.tag.ITagHandler;
import edu.chl.mailbowser.tag.ITag;
import edu.chl.mailbowser.tag.Tag;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by filip on 04/05/15.
 */
public class SidebarPresenter implements IObserver, Initializable {

    private ITagHandler tagHandler = MainHandler.INSTANCE.getTagHandler();

    @FXML private ListView<SidebarViewItemPresenter> tagsList;
    private ObservableList<SidebarViewItemPresenter> observableTagsList = FXCollections.observableList(new ArrayList<>());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.INSTANCE.register(this);

        observableTagsList.add(new SidebarViewItemPresenter("All emails"));

        for(ITag tag : tagHandler.getTags()) {
            observableTagsList.add(new SidebarViewItemPresenter((Tag)tag));
        }

        updateView();

        tagsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                EventBus.INSTANCE.publish(new Event(EventType.SELECT_TAG, newValue.getTag()));
            }
        });

    }

    private void updateView() {
        for(SidebarViewItemPresenter item : observableTagsList) {
            updateView(item);
        }
        tagsList.setItems(observableTagsList);
    }

    private void updateView(Set<ITag> tags) {
        if (!tags.isEmpty()) {
            for (ITag tag : tags) {
                updateView(tag);
            }
        }
        tagsList.setItems(observableTagsList);
    }

    private void updateView(SidebarViewItemPresenter item) {
        if(!observableTagsList.contains(item)) {
            observableTagsList.add(item);
        }
    }

    private void updateView(ITag tag) {
        SidebarViewItemPresenter item = new SidebarViewItemPresenter((Tag)tag);
        if(!observableTagsList.contains(item)) {
            observableTagsList.add(item);
        }
        tagsList.setItems(observableTagsList);
    }

    @Override
    public void onEvent(IEvent evt) {
        Platform.runLater( // JavaFX can get thread problems otherwise
                () -> handleEvent(evt)
        );
    }

    private void handleEvent(IEvent evt){
        switch (evt.getType()) {
            case FETCHED_EMAIL:
                updateView(tagHandler.getTags());
                break;
            case ADDED_TAG_TO_EMAIL:
                updateView(tagHandler.getTags());
                break;
            case REMOVED_TAG_FROM_EMAIL:
                updateView(tagHandler.getTags());
                break;
            case DELETED_TAG:
                updateView(tagHandler.getTags());
                break;
        }
    }

}

