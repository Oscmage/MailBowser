package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.contact.Contact;
import edu.chl.mailbowser.contact.IContact;
import edu.chl.mailbowser.contact.IContactBook;
import edu.chl.mailbowser.email.models.Address;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.util.List;
import java.util.Observable;


/**
 * Created by jesper on 2015-05-22.
 */
public class ContactBookPresenter {
    @FXML private ListView<IContact> contactList;
    @FXML private Button saveButton;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button addNewAddressButton;
    @FXML private TextField lastNameField;
    @FXML private TextField firstNameField;
    @FXML private GridPane gridPane;

    private List<TextField> addresses;

    private int newAddressIndex = 1;

    private ObservableList<IContact> contacts = contactList.getItems();


    private IContactBook contactBook = MainHandler.INSTANCE.getContactBook();
    private IContact selectedContact;

    @FXML
    public void addContactButtonOnAction(ActionEvent actionEvent) {
        IContact newContact = new Contact();
        contactBook.addContact(newContact);
        contacts.add(newContact);
    }

    public void deleteButtonOnAction(ActionEvent actionEvent) {
        selectedContact = contactList.getSelectionModel().getSelectedItem();
        contactBook.removeContact(selectedContact);
        contacts.remove(selectedContact);
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
        selectedContact = contactList.getSelectionModel().getSelectedItem();
        selectedContact.setFirstName(firstNameField.getText());
        selectedContact.setLastName(lastNameField.getText());
        for(TextField textField : addresses){
            selectedContact.addAddress(new Address(textField.getText()));
        }
    }

    public void addNewAddressButtonOnAction(ActionEvent actionEvent) {
        TextField newTextField = new TextField();
        newAddressIndex++;
        gridPane.addRow(newAddressIndex, new Label("Address" + (newAddressIndex)), newTextField);
        addresses.add(newTextField);
    }
}
