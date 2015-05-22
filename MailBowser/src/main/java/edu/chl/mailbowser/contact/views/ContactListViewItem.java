package edu.chl.mailbowser.contact.views;

import edu.chl.mailbowser.contact.Contact;
import edu.chl.mailbowser.contact.IContact;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jesper on 2015-05-22.
 */
public class ContactListViewItem extends FlowPane{
    private IContact contact;

    public ContactListViewItem (IContact contact){
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
}
