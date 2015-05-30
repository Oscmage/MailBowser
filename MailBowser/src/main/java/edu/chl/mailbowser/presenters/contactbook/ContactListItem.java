package edu.chl.mailbowser.presenters.contactbook;

import edu.chl.mailbowser.contact.IContact;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
import javafx.application.Platform;
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
public class ContactListItem extends FlowPane implements Comparable<ContactListItem> {
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

        setContact(contact);
    }

    /**
     * Updates this ContactListItem to show information about another contact.
     * @param contact
     */
    public final void setContact(IContact contact) {
        this.contact = contact;
        updateNameLabel();
    }

    /**
     * Sets a nicer value to the "Name"-label depending on the name of the contact supplied in the constructor.
     *
     * This method is final because it is used in the constructor. Making it final prevents subclasses from overriding
     * it, so it's behaviour is guaranteed to always be the same.
     */
    private final void updateNameLabel() {
        String fullName = contact.getFullName();
        if (!fullName.isEmpty()) {
            setText(fullName);
        } else {
            setText("Empty contact...");
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
    private void setText(String text){
        nameLabel.setText(text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null) {
            return false;
        } else if (!this.getClass().equals(o.getClass())) {
            return false;
        }

        ContactListItem other = (ContactListItem) o;
        return contact.equals(other.getContact());
    }

    @Override
    public int hashCode() {
        return contact.hashCode();
    }

    @Override
    public int compareTo(ContactListItem o) {
        return this.contact.compareTo(o.getContact());
    }
}
