package edu.chl.mailbowser.presenters.contactbook;

import edu.chl.mailbowser.contact.IContact;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by jesper on 2015-05-22.
 *
 * ContactListItemPresenter
 */
public class ContactListItem extends FlowPane{
    @FXML private Label nameLabel;

    private IContact contact;

    public ContactListItem(IContact contact){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/contactbook/ContactListItem.fxml"));
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
     *
     * This method is final because it is used in the constructor. Making it final prevents subclasses from overriding
     * it, so it's behaviour is guaranteed to always be the same.
     */
    private final void initializeNameLabel() {
        String fullName = contact.getFullName();
        if (!fullName.isEmpty()) {
            setText(fullName);
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
