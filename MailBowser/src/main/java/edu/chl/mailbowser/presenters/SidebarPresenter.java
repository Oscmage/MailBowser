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

    TagList<TagListItem> tagList = new TagList<TagListItem>(TagList.Type.GLOBAL);
    ObservableList<TagListItem> observableTagList = FXCollections.observableArrayList();
    @FXML protected VBox sidebarContent;

    public SidebarPresenter() {

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
                EventBus.INSTANCE.publish(new Event(EventType.SELECT_TAG, ((TagListItem)newValue).getTag()));
            }
        });

        updateView();

        sidebarContent.getChildren().add(tagList);

    }

    /**
     * A general method for updating the sidebar with the correct tags.
     */
    private void updateView() {
        observableTagList.clear();
        for(ITag tag : tagHandler.getTags()) {
            if(!observableTagList.contains(tag)) {
                observableTagList.add(new TagListItem(tag, TagList.Type.GLOBAL));
            }
        }
        tagList.setItems(observableTagList);
    }

    @Override
    public void onEvent(IEvent evt) {
        Platform.runLater( // JavaFX can get thread problems otherwise
                () -> handleEvent(evt)
        );
    }

    private void handleEvent(IEvent evt) {
        switch (evt.getType()) {
            case DELETED_TAG:
                updateView();
                break;
            case REMOVED_TAG_FROM_EMAIL:
                updateView();
                break;
            case ADDED_TAG_TO_EMAIL:
                updateView();
                break;
        }
    }
}




