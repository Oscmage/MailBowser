package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.main.MainHandler;
import edu.chl.mailbowser.contact.Contact;
import edu.chl.mailbowser.contact.IContact;
import edu.chl.mailbowser.contact.IContactBook;
import edu.chl.mailbowser.email.Address;
import edu.chl.mailbowser.email.IAddress;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Created by jesper on 2015-05-22.
 */
public class ContactBookPresenter implements Initializable{
    @FXML protected ListView<ContactListItemPresenter> contactList;
    @FXML protected Button saveButton;
    @FXML protected Button addButton;
    @FXML protected Button deleteButton;
    @FXML protected Button addNewAddressButton;
    @FXML protected TextField lastNameField;
    @FXML protected TextField firstNameField;
    @FXML protected VBox contactForm;

    private final int ORIGINAL_INDEX = 1;

    private List<TextField> addresses = new ArrayList<>();

    private int newAddressIndex = ORIGINAL_INDEX;

    private ObservableList<ContactListItemPresenter> contactListItems = FXCollections.observableArrayList();

    private IContactBook contactBook = MainHandler.INSTANCE.getContactBook();
    private ContactListItemPresenter selectedContact;

    @FXML
    public void addContactButtonOnAction(ActionEvent actionEvent) {
        IContact newContact = new Contact();
        contactBook.addContact(newContact);
        contactListItems.add(new ContactListItemPresenter(newContact));
    }

    public void deleteButtonOnAction(ActionEvent actionEvent) {
        selectedContact = contactList.getSelectionModel().getSelectedItem();
        if(selectedContact != null) {
            contactBook.removeContact(selectedContact.getContact());
            contactListItems.remove(selectedContact);
        }
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
        selectedContact = contactList.getSelectionModel().getSelectedItem();
        if(selectedContact != null) {
            selectedContact.getContact().setFirstName(firstNameField.getText());
            selectedContact.getContact().setLastName(lastNameField.getText());
            for (TextField textField : addresses) {
                selectedContact.getContact().addAddress(new Address(textField.getText()));
            }
        }
    }

    public void addNewAddressButtonOnAction(ActionEvent actionEvent) {
        addAddressField();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contactList.setItems(contactListItems);
    }

    public void contactListOnMouseClicked(Event event) {
        selectedContact = contactList.getSelectionModel().getSelectedItem();
        
        addresses.clear();
        newAddressIndex = ORIGINAL_INDEX;
        lastNameField.setText(selectedContact.getContact().getLastName());
        firstNameField.setText(selectedContact.getContact().getFirstName());
        for (IAddress address : selectedContact.getContact().getEmailAddresses()) {
            addAddressField(address);
        }
    }

    private void addAddressField(IAddress address){
        TextField newTextField = new TextField();
        Label newLabel = new Label("Address " + (newAddressIndex));
        newAddressIndex++;
        if(address != null) {
            newTextField.setText(address.getString());
        }
        contactForm.getChildren().addAll(newLabel, newTextField);
        addresses.add(newTextField);
    }

    private void addAddressField(){
        addAddressField((IAddress)null);
    }

}
