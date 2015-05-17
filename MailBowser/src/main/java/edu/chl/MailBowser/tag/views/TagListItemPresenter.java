package edu.chl.mailbowser.tag.views;

import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
import edu.chl.mailbowser.tag.handlers.TagHandler;
import edu.chl.mailbowser.tag.models.ITag;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by OscarEvertsson on 12/05/15.
 */
public class TagListItemPresenter extends Pane implements Initializable, IObserver{

    private IEmail email;
    private ITag tag;

    @FXML protected Label tagLabel;

    public TagListItemPresenter(ITag tag) {
        this();

        this.tag = tag;
        tagLabel.setText(this.tag.getName());
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

    public ITag getTag(){
        return this.tag;
    }

    public boolean equals(Object o){
       return tag.equals(o);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML private void removeTagButtonOnAction(ActionEvent event) {
        TagHandler.getInstance().removeTag(this.email, this.tag);
    }

    @Override
    public void onEvent(IEvent evt) {
        switch (evt.getType()) {
            case SELECTED_EMAIL:
                this.email = (IEmail) evt.getValue();
                break;
        }
    }
}
