package edu.chl.mailbowser.email.views;

import edu.chl.mailbowser.email.models.Email;
import edu.chl.mailbowser.email.models.IEmail;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by filip on 07/05/15.
 */
public class EmailListViewItem extends Pane {

    @FXML private IEmail email;

    public EmailListViewItem(IEmail email) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/EmailListViewItem.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.email = email;
    }

    public IEmail getEmail() {
        return this.email;
    }

}
