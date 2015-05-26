package edu.chl.mailbowser.presenters;


import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.tag.Tag;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by filip on 07/05/15.
 */
public class SidebarViewItemPresenter extends HBox implements Initializable {

    private Tag tag;

    @FXML protected Label name;
    @FXML protected Button button;

    public SidebarViewItemPresenter() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/sidebar/SidebarViewItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("FXML-file not found");
        }

    }

    public SidebarViewItemPresenter(Tag tag) {
        this();

        this.tag = tag;

        name.setText(tag.getName());
    }

    public SidebarViewItemPresenter(String string) {
        this();
        name.setText(string);
        button.setDisable(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Tag getTag() {
        if(tag != null) {
            return tag;
        }
        return null;
    }

    public String getName() {
        if(tag != null) {
            return tag.getName();
        }
        return name.getText();
    }


    @FXML
    protected void removeTagFromSidebar(ActionEvent event) {
        EventBus.INSTANCE.publish(new Event(EventType.DELETE_TAG, this));
    }

    public boolean equals(Object o) {
        if(o instanceof SidebarViewItemPresenter) {
            if(tag != null) {
                return this.tag.equals(
                        ((SidebarViewItemPresenter)o).getTag()
                );
            } else {
                return name.getText().equals(
                        ((SidebarViewItemPresenter)o).getName()
                );
            }

        }
        return false;
    }

    @Override
    public int hashCode() {
        if(tag != null) {
            return this.tag.hashCode();
        } else {
            return getName().hashCode();
        }

    }
}
