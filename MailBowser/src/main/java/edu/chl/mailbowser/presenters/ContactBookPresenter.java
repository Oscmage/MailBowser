package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.contact.Contact;
import edu.chl.mailbowser.contact.IContact;
import edu.chl.mailbowser.contact.IContactBook;
import edu.chl.mailbowser.contact.views.ContactListViewItem;
import edu.chl.mailbowser.email.models.Address;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;


/**
 * Created by jesper on 2015-05-22.
 */
public class ContactBookPresenter implements Initializable{
    @FXML private ListView<ContactListViewItem> contactList;
    @FXML private Button saveButton;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button addNewAddressButton;
    @FXML private TextField lastNameField;
    @FXML private TextField firstNameField;
    @FXML private GridPane gridPane;

    private List<TextField> addresses = new ArrayList<>();

    private int newAddressIndex = 1;

    private ObservableList<ContactListViewItem> contactListItems = FXCollections.observableArrayList();

    private IContactBook contactBook = MainHandler.INSTANCE.getContactBook();
    private ContactListViewItem selectedContact;

    @FXML
    public void addContactButtonOnAction(ActionEvent actionEvent) {
        IContact newContact = new Contact();
        contactBook.addContact(newContact);
        contactListItems.add(new ContactListViewItem(newContact));
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
        selectedContact.getContact().setFirstName(firstNameField.getText());
        selectedContact.getContact().setLastName(lastNameField.getText());
        for(TextField textField : addresses){
            selectedContact.getContact().addAddress(new Address(textField.getText()));
        }
    }

    public void addNewAddressButtonOnAction(ActionEvent actionEvent) {
        TextField newTextField = new TextField();
        Label newLabel = new Label("Address" + (newAddressIndex));
        newAddressIndex++;
        gridPane.addRow(newAddressIndex, newLabel, newTextField);
        addresses.add(newTextField);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contactList.setItems(contactListItems);
    }
}
