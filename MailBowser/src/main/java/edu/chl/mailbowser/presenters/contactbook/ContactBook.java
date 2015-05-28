package edu.chl.mailbowser.presenters.contactbook;

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
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * Created by jesper on 2015-05-22.
 */
public class ContactBook extends VBox {

    @FXML protected ListView<ContactListItem> contactsList;
    @FXML protected Button saveContactButton;
    @FXML protected Button addContactButton;
    @FXML protected Button deleteContactButton;
    @FXML protected Button addNewAddressButton;
    @FXML protected TextField lastNameField;
    @FXML protected TextField firstNameField;
    @FXML protected VBox contactForm;
    @FXML protected VBox nameForm;
    @FXML protected VBox addressForm;
    @FXML protected HBox menuBarLeft;
    @FXML protected HBox menuBarRight;

    private final int ORIGINAL_INDEX = 1;
    private int newAddressIndex = ORIGINAL_INDEX;

    private ObservableList<ContactListItem> contactListItems = FXCollections.observableArrayList();

    private IContactBook contactBook = MainHandler.INSTANCE.getContactBook();
    private ContactListItem selectedContact;

    public ContactBook() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/contactbook/ContactBook.fxml"));
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

    public ContactBook(boolean showInsertButton) {
        this();

        if (showInsertButton) {
            firstNameField.setEditable(false);
            lastNameField.setEditable(false);

            Button button = new Button("\uf0ab");
            button.getStyleClass().addAll("circle", "fontawesome");
            button.setOnAction(event -> {
                EventBus.INSTANCE.publish(new Event(EventType.INSERT_CONTACT_TO_EMAIL,
                        contactsList.getSelectionModel().getSelectedItem().getContact()
                ));
            });

            menuBarRight.getChildren().setAll(button);
            menuBarLeft.getChildren().clear();
        }
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
            contactListItems.add(new ContactListItem(contact));
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
        IContact contact = selectedContact.getContact();
        addressForm.getChildren().clear();
        newAddressIndex = 1;

        lastNameField.setText(contact.getLastName());
        firstNameField.setText(contact.getFirstName());
        for (IAddress address : contact.getEmailAddresses()) {
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
        TextField newAddress = new TextField();
        newAddress.setPromptText("Address" + newAddressIndex);
        newAddressIndex++;

        if(address != null) {
            newAddress.setText(address.getString());
        }

        addressForm.getChildren().add(newAddress);
    }

    /**
     * Adds a new TextField to the "Add contact"-form for assigning yet another address to the selected contact.
     */
    private void addAddressField() {
        TextField newAddress = new TextField();
        newAddress.setPromptText("Address" + newAddressIndex);
        newAddressIndex++;
        addressForm.getChildren().addAll(newAddress);
    }

    /**
     * Removes the most recently added address field.
     */
    private void removeAddressField() {

        ObservableList<Node> addresses = addressForm.getChildren();

        if(!addressForm.getChildren().isEmpty()) {
            TextField latestAdded = (TextField)addresses.get(addresses.size() - 1);
            addresses.remove(latestAdded);
            newAddressIndex--;
        }
    }

    /**
     * Makes sure no name field is empty, and validates correct
     * email addresses in the the address fields. Also applies some CSS-styles to invalid fields.
     * @return True if form is valid, otherwise false.
     */
    private boolean validateForm() {

        boolean foundErrors = false;

        for(Node node : nameForm.getChildren()) {

            ObservableList<String> styles = node.getStyleClass();
            if(node instanceof TextField) {
                if(((TextField) node).getText().equals("")) {
                    if (!styles.contains("error")) {
                        styles.add("error");
                    }
                    foundErrors = true;
                } else {
                    node.getStyleClass().remove("error");
                }
            }
        }

        for(Node node : addressForm.getChildren()) {

            ObservableList<String> styles = node.getStyleClass();
            if(node instanceof TextField) {
                if(!Address.isValidAddress(((TextField)node).getText())) {
                    if (!styles.contains("error")) {
                        styles.add("error");
                    }
                    foundErrors = true;
                } else {
                    node.getStyleClass().remove("error");
                }
            }
        }

        return !foundErrors;

    }

    /**
     * Toggles the "error" CSS-class on Nodes.
     */
    private void toggleErrorClass(Node node) {
        if(node.getStyleClass().contains("error")) {
            node.getStyleClass().remove("error");
        } else {
            node.getStyleClass().add("error");
        }
    }

    /**
     * Invoked when the "Add contact"-button is clicked.
     * @param actionEvent
     */
    @FXML
    public void addContactButtonOnAction(ActionEvent actionEvent) {
        IContact newContact = new Contact();
        ContactListItem contactListItem = new ContactListItem(newContact);
        contactBook.addContact(newContact);
        contactListItems.add(contactListItem);
        contactsList.getSelectionModel().select(contactListItem);
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

        if(validateForm()) {
            IContact contact = selectedContact.getContact();
            contact.setFirstName(firstNameField.getText());
            contact.setLastName(lastNameField.getText());

            contact.removeAllAddresses();
            System.out.println(contact.getEmailAddresses().size());

            for (Node textField : addressForm.getChildren()) {
                contact.addAddress(new Address(((TextField)textField).getText()));
            }
            for (Node node : contactForm.getChildren()) {
                node.getStyleClass().remove("error");
            }
            System.out.println("Saved contact!");
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
