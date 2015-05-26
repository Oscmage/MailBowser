package edu.chl.mailbowser.tag.views;

import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.tag.models.ITag;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by OscarEvertsson on 12/05/15.
 * This class creates a new tagItem in the gui with the specified tag.
 */
public class TagListItemPresenter extends HBox implements Initializable{

    private ITag tag;

    @FXML protected Label name;

    /**
     * Constructs a listItem with the specified tag and loads the related fxml.
     * @param tag
     */
    public TagListItemPresenter(ITag tag) {
        this();
        this.tag = tag;
        name.setText(this.tag.getName());
    }

    /**
     * Loads the related fxml and specifies this class as the presenter for it.
     */
    public TagListItemPresenter() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TagListItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    public void removeTagFromEmail() {
        EventBus.INSTANCE.publish(new Event(EventType.REMOVE_TAG_FROM_EMAIL, tag));
    }

    /**
     * Returns the tag for the listItem.
     * @return
     */
    public ITag getTag(){
        return this.tag;
    }

    /**
     * Returns true if this presenter uses the same tag as the specified object.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o){
       return tag.equals(o);
    }

    /**
     * This method ONLY exists due to the fact that javaFX needs it to be here.
     * FXMLLoader won't be able to load without it.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
