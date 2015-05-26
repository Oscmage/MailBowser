package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.tag.Tag;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Created by filip on 21/05/15.
 */
public class AddTagPresenter extends VBox {

    @FXML protected TextField tagTextField;
    @FXML protected Button tagButton;
    
    public AddTagPresenter() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddTagView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Closes the "Add tag"-window.
     */
    private void closeWindow() {
        ((Stage)this.getScene().getWindow()).close();
    }

    /**
     * Invoked when the "Tag"-button is clicked.
     *
     * @param actionEvent
     */
    @FXML
    public void tagButtonOnAction(ActionEvent actionEvent) {
        if(!tagTextField.getText().equals("")) {
            EventBus.INSTANCE.publish(new Event(EventType.ADD_TAG_TO_EMAIL, new Tag(tagTextField.getText())));
            closeWindow();
        }
    }

    /**
     * Invoked when the "Cancel"-button is clicked.
     *
     * @param actionEvent
     */
    @FXML
    public void cancelButtonOnAction(ActionEvent actionEvent) {
        closeWindow();
    }

}
