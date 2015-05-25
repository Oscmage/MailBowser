package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.contact.IContact;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

/**
 * Created by jesper on 2015-05-22.
 *
 * ContactListItemPresenter
 */
public class ContactListItemPresenter extends FlowPane{
    @FXML private Label nameLabel;

    private IContact contact;

    public ContactListItemPresenter(IContact contact){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ContactListViewItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("FXML-file not found");
        }
        this.contact = contact;
    }

    public IContact getContact(){
        return contact;
    }

    public void setText(String text){
        nameLabel.setText(text);
    }
}
