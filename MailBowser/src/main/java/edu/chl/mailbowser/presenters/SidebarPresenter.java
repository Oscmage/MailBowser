package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.event.*;
import edu.chl.mailbowser.main.MainHandler;
import edu.chl.mailbowser.presenters.tag.TagList;
import edu.chl.mailbowser.presenters.tag.TagListItem;
import edu.chl.mailbowser.tag.ITag;
import edu.chl.mailbowser.tag.ITagHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SidebarPresenter extends VBox implements IObserver{

    ITagHandler tagHandler = MainHandler.INSTANCE.getTagHandler();

    TagList tagList = new TagList(tagHandler.getTags(), TagList.Type.GLOBAL);

    @FXML protected VBox sidebarContent;

    public SidebarPresenter() {
        EventBus.INSTANCE.register(this);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SidebarView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println(e);
        }

        tagList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                EventBus.INSTANCE.publish(new Event(EventType.SELECT_TAG, newValue.getTag()));
            }
        });

        sidebarContent.getChildren().add(tagList);
    }

    /**
     * Adds a tag to the list of tags if it isn't added since before.
     *
     * @param tag the tag to remove
     */
    public void addTagToList(ITag tag) {
        tagList.addTag(tag);
    }

    /**
     * Removes a tag from the list of tags.
     *
     * @param tag the tag to remove
     */
    public void removeTagFromList(ITag tag) {
        tagList.removeTag(tag);
    }

    @Override
    public void onEvent(IEvent evt) {
        Platform.runLater( // JavaFX can get thread problems otherwise
                () -> handleEvent(evt)
        );
    }

    private void handleEvent(IEvent evt) {
        switch (evt.getType()) {
            case NEW_TAG_ADDED:
                addTagToList((ITag) evt.getValue());
                break;
            case REMOVED_TAG_COMPLETELY:
                removeTagFromList((ITag) evt.getValue());
                break;
        }
    }
}




