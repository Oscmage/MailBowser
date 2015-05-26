package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.main.MainHandler;
import edu.chl.mailbowser.contact.Contact;
import edu.chl.mailbowser.contact.IContact;
import edu.chl.mailbowser.contact.IContactBook;
import edu.chl.mailbowser.email.Address;
import edu.chl.mailbowser.email.IAddress;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by jesper on 2015-05-22.
 */
public class ContactBookPresenter extends VBox {

    @FXML protected ListView<ContactListItemPresenter> contactsList;
    @FXML protected Button saveContactButton;
    @FXML protected Button addContactButton;
    @FXML protected Button deleteContactButton;
    @FXML protected Button addNewAddressButton;
    @FXML protected TextField lastNameField;
    @FXML protected TextField firstNameField;
    @FXML protected VBox contactForm;
    @FXML protected HBox menuBarLeft;
    @FXML protected HBox menuBarRight;

    private final int ORIGINAL_INDEX = 1;
    private List<TextField> addresses = new ArrayList<>();

    private int newAddressIndex = ORIGINAL_INDEX;

    private ObservableList<ContactListItemPresenter> contactListItems = FXCollections.observableArrayList();

    private IContactBook contactBook = MainHandler.INSTANCE.getContactBook();
    private ContactListItemPresenter selectedContact;

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

        initializeContactBook();
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

    /**
     * Populates the contact list, adds listeners to its items and disables controls if no contact is selected.
     */
    private void initializeContactBook() {
        contactsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedContact = newValue;
                updateView();
            }
        });

        for(IContact contact : contactBook.getContacts()) {
            contactListItems.add(new ContactListItemPresenter(contact));
        }

        contactsList.setItems(contactListItems);

        if(contactListItems.isEmpty()) {
            firstNameField.setEditable(false);
            lastNameField.setEditable(false);
            toggleDisableButtons(true);
        }

    }

    /**
     * Toggles the "Disabled"-attribute on buttons in the top right side of the menubar.
     * @param disableButtons
     */
    private void toggleDisableButtons(boolean disableButtons) {
        List<Node> buttons = menuBarRight.getChildren().stream()
                .filter(t -> t.getClass() == Button.class)
                .map(o -> (Button) o)
                .collect(Collectors.toList());

        if(disableButtons) {
            buttons.stream()
                    .forEach(s -> s.setDisable(true));
        } else {
            buttons.stream()
                    .forEach(s -> s.setDisable(false));
        }
    }

    /**
     * A general method for updating the view when a contact is selected.
     */
    private void updateView() {
        lastNameField.setText(selectedContact.getContact().getLastName());
        firstNameField.setText(selectedContact.getContact().getFirstName());
        for (IAddress address : selectedContact.getContact().getEmailAddresses()) {
            addAddressField(address);
        }
        firstNameField.setEditable(true);
        lastNameField.setEditable(true);
        toggleDisableButtons(false);
    }

    /**
     * Adds a new TextField to the "Add contact"-form and populates it with the address supplied as parameter.
     *
     * @param address
     */
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

    /**
     * Adds a new TextField to the "Add contact"-form for assigning yet another address to the selected contact.
     */
    private void addAddressField() {
        TextField newTextField = new TextField();
        Label newLabel = new Label("Address " + (newAddressIndex));
        newAddressIndex++;
        contactForm.getChildren().addAll(newLabel, newTextField);
        addresses.add(newTextField);
    }

    /**
     * Removes the most recently added address field.
     */
    private void removeAddressField() {
        if(!addresses.isEmpty()) {
            TextField latestAdded = addresses.get(addresses.size() - 1);
            addresses.remove(latestAdded);
            contactForm.getChildren().remove(latestAdded);
            contactForm.getChildren().remove(contactForm.getChildren().size() - 1);
            newAddressIndex--;
        }
    }

    /**
     * Invoked when the "Add contact"-button is clicked.
     * @param actionEvent
     */
    @FXML
    public void addContactButtonOnAction(ActionEvent actionEvent) {
        IContact newContact = new Contact();
        ContactListItemPresenter contactListItemPresenter = new ContactListItemPresenter(newContact);
        contactBook.addContact(newContact);
        contactListItems.add(contactListItemPresenter);
        contactsList.getSelectionModel().select(contactListItemPresenter);
    }

    /**
     * Invoked when the "Delete contact"-button is clicked.
     * @param actionEvent
     */
    @FXML
    protected void deleteContactButtonOnAction(ActionEvent actionEvent) {
        contactBook.removeContact(selectedContact.getContact());
        contactListItems.remove(selectedContact);
    }

    /**
     * Invoked when the "Save contact"-button is clicked.
     * @param actionEvent
     */
    @FXML
    protected void saveContactButtonOnAction(ActionEvent actionEvent) {
        selectedContact.getContact().setFirstName(firstNameField.getText());
        selectedContact.getContact().setLastName(lastNameField.getText());
        for (TextField textField : addresses) {
            selectedContact.getContact().addAddress(new Address(textField.getText()));
        }
    }

    /**
     * Invoked when the "Add address"-button is clicked.
     * @param actionEvent
     */
    @FXML
    protected void addNewAddressButtonOnAction(ActionEvent actionEvent) {
        addAddressField();
    }

    /**
     * Invoked when the "Remove address"-button is clicked.
     * @param actionEvent
     */
    @FXML
    protected void removeAddressButtonOnAction(ActionEvent actionEvent) {
        removeAddressField();
    }

}
