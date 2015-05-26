package edu.chl.mailbowser.presenters.tag;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;

import java.io.IOException;

/**
 * Created by filip on 04/05/15.
 */
public class TagList<TagListItem> extends ListView {

    /**
     * Setting type to GLOBAL means the list will display all existing tags. LOCAL means no tags are selectable
     * and is supposed to be used where the programmer wants to display only the tags related to one email.
     */
    public enum Type {
        GLOBAL,
        LOCAL
    }

    public Type type;

    public TagList() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tag/TagList.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e.getCause());
        }
    }

    public TagList(Type type) {
        this();
        this.type = type;
    }

    /**
     * Returns the type of the TagList.
     * @return
     */
    public Type getType() {
        return type;
    }

}

