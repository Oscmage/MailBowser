package edu.chl.mailbowser.presenters.tag;

import edu.chl.mailbowser.main.MainHandler;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.event.*;
import edu.chl.mailbowser.tag.ITagHandler;
import edu.chl.mailbowser.tag.ITag;
import edu.chl.mailbowser.tag.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by filip on 04/05/15.
 */
public class TagList<T> extends ListView {

    public enum Type {
        GLOBAL,
        LOCAL
    }

    private ITagHandler tagHandler = MainHandler.INSTANCE.getTagHandler();
    private ObservableList<TagListItem> observableTagsList = FXCollections.observableList(new ArrayList<>());
    public Type type;

    @FXML protected ListView<TagListItem> tagsList;

    public TagList() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tag/TagList.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e.getCause());
        }

        updateView();
    }

    public TagList(Type type) {
        this();

        this.type = type;
        if(type == Type.GLOBAL) {

            tagsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    EventBus.INSTANCE.publish(new Event(EventType.SELECT_TAG, newValue.getTag()));
                }
            });

            for(ITag tag : tagHandler.getTags()) {
                observableTagsList.add(new TagListItem(tag, type));
            }

        }
    }

    private void updateView() {
        for(TagListItem item : observableTagsList) {
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

    private void updateView(TagListItem item) {
        if(!observableTagsList.contains(item)) {
            observableTagsList.add(item);
        }
    }

    private void updateView(ITag tag) {
        TagListItem item = new TagListItem((Tag)tag, type);
        if(!observableTagsList.contains(item)) {
            observableTagsList.add(item);
        }
        tagsList.setItems(observableTagsList);
    }

}

