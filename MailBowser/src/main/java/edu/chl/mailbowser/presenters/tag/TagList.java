package edu.chl.mailbowser.presenters.tag;

import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
import edu.chl.mailbowser.tag.ITag;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.Set;

/**
 * Created by filip on 04/05/15.
 */
public class TagList extends ListView<TagListItem> {

    /**
     * Setting type to GLOBAL means the list will display all existing tags. LOCAL means no tags are selectable
     * and is supposed to be used where the programmer wants to display only the tags related to one email.
     */
    public enum Type {
        GLOBAL,
        LOCAL
    }

    public Type type;

    private ObservableList<TagListItem> tagList = FXCollections.observableArrayList();

    public TagList() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tag/TagList.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e.getCause());
        }

        this.setItems(tagList);
    }

    public TagList(Type type) {
        this();
        this.type = type;
    }

    public TagList(Set<ITag> tags, Type type) {
        this(type);
        setTags(tags);
    }

    /**
     * Sets the type to use when displaying this tag list.
     *
     * @param type the type to use
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Returns the type of the TagList.
     * @return
     */
    public Type getType() {
        return type;
    }

    /**
     * Adds all tags to the list.
     *
     * @param tags the tags to add
     */
    public void setTags(Set<ITag> tags) {
        for (ITag tag : tags) {
            tagList.add(new TagListItem(tag, type));
        }
    }

    /**
     * Adds a tag to the list
     *
     * @param tag the tag to add
     */
    public void addTag(ITag tag) {
        tagList.add(new TagListItem(tag, type));
    }

    /**
     * Removes a tag from the list.
     *
     * @param tag the tag to remove
     */
    public void removeTag(ITag tag) {
        tagList.remove(new TagListItem(tag, type));
    }

    /**
     * Removes all added tags from the list.
     */
    public void clear() {
        tagList.clear();
    }
}

