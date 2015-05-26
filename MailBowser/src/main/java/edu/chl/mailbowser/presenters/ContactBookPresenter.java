package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.contact.Contact;
import edu.chl.mailbowser.contact.IContact;
import edu.chl.mailbowser.contact.IContactBook;
import edu.chl.mailbowser.contact.views.ContactListViewItem;
import edu.chl.mailbowser.email.models.Address;
import edu.chl.mailbowser.email.models.IAddress;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Created by jesper on 2015-05-22.
 */
public class ContactBookPresenter extends VBox {

    @FXML protected ListView<ContactListViewItem> contactsList;
    @FXML protected Button saveButton;
    @FXML protected Button addButton;
    @FXML protected Button deleteButton;
    @FXML protected Button addNewAddressButton;
    @FXML protected TextField lastNameField;
    @FXML protected TextField firstNameField;
    @FXML protected VBox contactForm;
    @FXML protected HBox menuBarLeft;
    @FXML protected HBox menuBarRight;

    private final int ORIGINAL_INDEX = 1;

    private List<TextField> addresses = new ArrayList<>();

    private int newAddressIndex = ORIGINAL_INDEX;

    private ObservableList<ContactListViewItem> contactListItems = FXCollections.observableArrayList();

    private IContactBook contactBook = MainHandler.INSTANCE.getContactBook();
    private ContactListViewItem selectedContact;

    public ContactBookPresenter() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ContactBookView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("FXML-file not found in " + fxmlLoader.getLocation());
        }

        contactsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedContact = newValue;
                updateView();
            }
        });

        for(IContact contact : contactBook.getContacts()) {
            contactListItems.add(new ContactListViewItem(contact));
        }

        contactsList.setItems(contactListItems);
    }

    public ContactBookPresenter(boolean showInsertButton) {
        this();
        menuBarLeft.getChildren().clear();
        menuBarRight.getChildren().clear();

        firstNameField.setEditable(false);
        lastNameField.setEditable(false);

        Button button = new Button("\uf0ab");
        button.getStyleClass().addAll("circle", "fontawesome");
        button.setOnAction(event -> {
            EventBus.INSTANCE.publish(new Event(EventType.INSERT_CONTACT_TO_EMAIL,
                    contactsList.getSelectionModel().getSelectedItem().getContact()
            ));
        });
        menuBarRight.getChildren().add(button);
    }

    private void updateView() {
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

    private void addAddressField() {
        TextField newTextField = new TextField();
        Label newLabel = new Label("Address " + (newAddressIndex));
        newAddressIndex++;
        contactForm.getChildren().addAll(newLabel, newTextField);
        addresses.add(newTextField);
    }

    @FXML
    public void addContactButtonOnAction(ActionEvent actionEvent) {
        IContact newContact = new Contact();
        contactBook.addContact(newContact);
        contactListItems.add(new ContactListViewItem(newContact));
    }

    @FXML
    protected void deleteButtonOnAction(ActionEvent actionEvent) {
        contactBook.removeContact(selectedContact.getContact());
        contactListItems.remove(selectedContact);
    }

    @FXML
    protected void saveButtonOnAction(ActionEvent actionEvent) {
        selectedContact.getContact().setFirstName(firstNameField.getText());
        selectedContact.getContact().setLastName(lastNameField.getText());
        for (TextField textField : addresses) {
            selectedContact.getContact().addAddress(new Address(textField.getText()));
        }
    }

    @FXML
    protected void addNewAddressButtonOnAction(ActionEvent actionEvent) {
        addAddressField();
    }

}
