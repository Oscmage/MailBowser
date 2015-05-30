package edu.chl.mailbowser.presenters.contactbook;

import edu.chl.mailbowser.event.*;
import edu.chl.mailbowser.main.MainHandler;
import edu.chl.mailbowser.contact.Contact;
import edu.chl.mailbowser.contact.IContact;
import edu.chl.mailbowser.contact.IContactBook;
import edu.chl.mailbowser.email.Address;
import edu.chl.mailbowser.email.IAddress;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by jesper on 2015-05-22.
 */
public class ContactBook extends VBox implements IObserver {

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

    private ObservableList<ContactListItem> contactListItems = FXCollections.observableArrayList();
    private SortedList<ContactListItem> sortedContactListItems = new SortedList<>(contactListItems,
            Comparator.<ContactListItem>naturalOrder());

    private IContactBook contactBook = MainHandler.INSTANCE.getContactBook();

    public ContactBook() {
        EventBus.INSTANCE.register(this);

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
     * Populates the contact list, adds listeners to its items and disables controls if there are no contacts.
     */
    private void initializeContactBook() {
        contactsList.setItems(sortedContactListItems);

        contactsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                disableFieldsAndButtons();
            } else {
                enableFieldsAndButtons();
            }
            updateEditView(newValue);
            removeErrorIndications();
        });

        for(IContact contact : contactBook.getContacts()) {
            contactListItems.add(new ContactListItem(contact));
        }

        // disable fields and buttons if there are no contacts, otherwise select the first contact in the list
        if (contactListItems.isEmpty()) {
            disableFieldsAndButtons();
        } else {
            contactsList.getSelectionModel().select(0);
        }
    }

    /**
     * A general method for updating the view when a contact is selected.
     *
     * Updates the edit view to match a selected item in the list.
     */
    private void updateEditView(ContactListItem selectedItem) {
        addressForm.getChildren().clear();
        if (selectedItem == null) {
            lastNameField.setText("");
            firstNameField.setText("");
        } else {
            IContact contact = selectedItem.getContact();
            lastNameField.setText(contact.getLastName());
            firstNameField.setText(contact.getFirstName());

            for (IAddress address : contact.getEmailAddresses()) {
                addAddressField(address);
            }
        }
    }

    /**
     * Adds a new TextField to the "Add contact"-form. If the supplied address isn't null, the TextFields value
     * will be wet to the value of the address.
     *
     * @param address
     */
    private void addAddressField(IAddress address){
        ObservableList<Node> addressFormFields = addressForm.getChildren();

        TextField newAddress = new TextField();
        newAddress.setPromptText("Address " + (addressFormFields.size() + 1));

        if (address != null) {
            newAddress.setText(address.getString());
        }

        addressFormFields.add(newAddress);
    }

    /**
     * Disables buttons and editing of fields.
     */
    private void disableFieldsAndButtons() {
        firstNameField.setEditable(false);
        lastNameField.setEditable(false);
        toggleDisableButtons(true);
    }

    /**
     * Enabled buttons and editing of fields.
     */
    private void enableFieldsAndButtons() {
        firstNameField.setEditable(true);
        lastNameField.setEditable(true);
        toggleDisableButtons(false);
    }

    /**
     * Toggles the "Disabled"-attribute on buttons in the top right side of the menubar.
     *
     * @param disableButtons true if buttons should be disabled, false if they should be enabled
     */
    private void toggleDisableButtons(boolean disableButtons) {
        List<Node> buttons = menuBarRight.getChildren().stream()
                .filter(t -> t.getClass() == Button.class)
                .map(o -> (Button) o)
                .collect(Collectors.toList());

        buttons.stream()
                .forEach(s -> s.setDisable(disableButtons));
    }

    /**
     * Removes the most recently added address field.
     */
    private void removeAddressField() {
        ObservableList<Node> addressFormFields = addressForm.getChildren();

        if (!addressFormFields.isEmpty()) {
            addressFormFields.remove(addressFormFields.size() - 1);
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

    private void removeErrorIndications() {
        for(Node node : nameForm.getChildren()) {

            ObservableList<String> styles = node.getStyleClass();
            if(node instanceof TextField) {
                styles.remove("error");
            }
        }

        for(Node node : addressForm.getChildren()) {
            ObservableList<String> styles = node.getStyleClass();
            if(node instanceof TextField) {
                styles.remove("error");
            }
        }
    }

    /**
     * Adds a contact to the list of contacts.
     *
     * @param contact the contact to add
     */
    public void addContactToList(IContact contact) {
        ContactListItem contactListItem = new ContactListItem(contact);
        contactListItems.add(contactListItem);
        contactsList.getSelectionModel().select(contactListItem);
    }

    /**
     * Removes a contact form the list of contacts.
     *
     * @param contact the contact to remove
     */
    public void removeContactFromList(IContact contact) {
        ContactListItem contactListItem = new ContactListItem(contact);
        contactListItems.remove(contactListItem);
    }

    /**
     * Invoked when the "Add contact"-button is clicked.
     * @param actionEvent
     */
    @FXML
    public void addContactButtonOnAction(ActionEvent actionEvent) {
        IContact newContact = new Contact();
        contactBook.addContact(newContact);
    }

    /**
     * Invoked when the "Delete contact"-button is clicked.
     * @param actionEvent
     */
    @FXML
    protected void deleteContactButtonOnAction(ActionEvent actionEvent) {
        ContactListItem selectedItem = contactsList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            contactBook.removeContact(selectedItem.getContact());
        }
    }

    /**
     * Invoked when the "Save contact"-button is clicked.
     * @param actionEvent
     */
    @FXML
    protected void saveContactButtonOnAction(ActionEvent actionEvent) {
        if (validateForm()) {
            ContactListItem selectedItem = contactsList.getSelectionModel().getSelectedItem();
            IContact selectedContact = selectedItem.getContact();

            List<IAddress> addresses = new ArrayList<>();
            for (Node textField : addressForm.getChildren()) {
                addresses.add(new Address(((TextField) textField).getText()));
            }
            IContact newContact = new Contact(firstNameField.getText(), lastNameField.getText(), addresses);

            contactBook.removeContact(selectedContact);
            contactBook.addContact(newContact);
        }
    }

    /**
     * Invoked when the "Add address"-button is clicked.
     * @param actionEvent
     */
    @FXML
    protected void addNewAddressButtonOnAction(ActionEvent actionEvent) {
        addAddressField(null);
    }

    /**
     * Invoked when the "Remove address"-button is clicked.
     * @param actionEvent
     */
    @FXML
    protected void removeAddressButtonOnAction(ActionEvent actionEvent) {
        removeAddressField();
    }

    @Override
    public void onEvent(IEvent evt) {
        Platform.runLater(() -> {
            handleEvent(evt);
        });
    }

    private void handleEvent(IEvent evt) {
        switch (evt.getType()) {
            case CONTACT_ADDED:
                addContactToList((IContact) evt.getValue());
                break;
            case CONTACT_REMOVED:
                removeContactFromList((IContact) evt.getValue());
                break;
        }
    }
}
