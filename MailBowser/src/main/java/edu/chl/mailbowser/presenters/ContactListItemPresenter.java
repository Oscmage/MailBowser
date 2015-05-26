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
        initializeNameLabel();
    }

    /**
     * Sets a nicer value to the "Name"-label depending on the name of the contact supplied in the constructor.
     */
    private void initializeNameLabel() {
        if(contact.getFirstName() != null && contact.getLastName() != null) {
            setText(contact.getFullName());
        } else if((contact.getFirstName() != null)) {
            setText(contact.getFirstName());
        } else if((contact.getLastName() != null)) {
            setText(contact.getLastName());
        } else {
            setText("New contact...");
        }
    }

    /**
     * Returns the contact assigned to this list item.
     *
     * @return
     */
    public IContact getContact(){
        return contact;
    }

    /**
     * Sets a value to the "Name"-label.
     * @param text
     */
    public void setText(String text){
        nameLabel.setText(text);
    }
}
