package edu.chl.mailbowser.presenters.tag;


import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.tag.ITag;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.io.IOException;

/**
 * Created by filip on 07/05/15.
 */
public class TagListItem extends HBox implements Comparable<TagListItem> {

    private ITag tag;
    private TagList.Type type;

    @FXML protected Button button;
    @FXML protected Label tagName;

    private TagListItem() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tag/TagListItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public TagListItem(ITag tag, TagList.Type type) {
        this();

        if(type == TagList.Type.GLOBAL) {
            this.getStyleClass().add("global-tag");
        } else if (type == TagList.Type.LOCAL) {
            this.getStyleClass().add("tag");
            this.getStyleClass().add("local-tag");
        }

        this.tag = tag;
        this.type = type;
        tagName.setText(tag.getName());
    }

    public TagListItem(String string, TagList.Type type) {
        this();
        this.getChildren().remove(button);
        this.type = type;
        tagName.setText(string);
    }

    public ITag getTag() {
        if(tag != null) {
            return tag;
        }
        return null;
    }

    public String getName() {
        if(tag != null) {
            return tag.getName();
        }
        return tagName.getText();
    }


    @FXML
    protected void removeTag() {
        if (type == TagList.Type.GLOBAL) {
            EventBus.INSTANCE.publish(new Event(EventType.DELETE_TAG, tag));
        } else if(type == TagList.Type.LOCAL) {
            EventBus.INSTANCE.publish(new Event(EventType.REMOVE_TAG_FROM_EMAIL, tag));
        }
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null) {
            return false;
        } else if (!o.getClass().equals(this.getClass())) {
            return false;
        }

        TagListItem other = (TagListItem) o;

        return tag != null && other.getTag() != null
                && tag.equals(other.getTag());
    }

    @Override
    public int hashCode() {
        if(tag != null) {
            return this.tag.hashCode();
        } else {
            return getName().hashCode();
        }

    }

    @Override
    public int compareTo(TagListItem o) {
        return tag.compareTo(tag);
    }
}
