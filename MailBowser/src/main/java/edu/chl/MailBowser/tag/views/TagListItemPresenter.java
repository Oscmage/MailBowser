package edu.chl.mailbowser.tag.views;

import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.tag.models.ITag;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by OscarEvertsson on 12/05/15.
 */
public class TagListItemPresenter extends Pane implements Initializable{


    private ITag tag;

    @FXML protected Label tagLabel;

    public TagListItemPresenter(ITag tag) {
        this();

        this.tag = tag;
        tagLabel.setText(tag.getName());
    }

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

    public void remove(){
        tagLabel.setText("");
    }

    private void removeTagActionPerformed(ActionEvent evt){
        EventBus.INSTANCE.publish(new Event(EventType.REMOVE_TAG, this.tag));
    }

    public ITag getTag(){
        return this.tag;
    }

    public boolean equals(Object o){
       return tag.equals(o);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
