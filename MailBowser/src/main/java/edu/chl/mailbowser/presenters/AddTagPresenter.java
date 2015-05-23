package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.tag.handlers.ITagHandler;
import edu.chl.mailbowser.tag.models.Tag;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by filip on 21/05/15.
 */
public class AddTagPresenter extends VBox implements Initializable {

    private IEmail email;
    private ITagHandler tagHandler = MainHandler.INSTANCE.getTagHandler();

    @FXML protected TextField tagTextField;
    @FXML protected Button tagButton;

    public AddTagPresenter(IEmail email) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddTagView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println(e);
        }
        this.email = email;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void tagButtonOnAction(ActionEvent actionEvent) {
        tagHandler.addTag(email, new Tag(tagTextField.getText()));
    }

}
