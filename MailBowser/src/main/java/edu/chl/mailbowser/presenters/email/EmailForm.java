package edu.chl.mailbowser.presenters.email;

import edu.chl.mailbowser.account.IAccount;
import edu.chl.mailbowser.main.MainHandler;
import edu.chl.mailbowser.account.IAccountHandler;
import edu.chl.mailbowser.email.Address;
import edu.chl.mailbowser.email.Email;
import edu.chl.mailbowser.email.IAddress;
import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.contact.IContact;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
import edu.chl.mailbowser.presenters.contactbook.ContactBook;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.markdown4j.Markdown4jProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mats on 09/04/15.
 */
public class EmailForm extends GridPane implements IObserver {

    private static final String EMAIL_CSS = "<head><style>* {font-family: \"Arial\"}</style></head>";
    private IAccountHandler accountHandler = MainHandler.INSTANCE.getAccountHandler();
    private String html = "";

    @FXML protected ChoiceBox<IAccount> fromChoiceBox;
    @FXML protected TextField toTextField;
    @FXML protected TextField ccTextField;
    @FXML protected TextField bccTextField;
    @FXML protected TextField subjectTextField;
    @FXML protected TextArea contentTextArea;
    @FXML protected Button sendButton;
    @FXML protected WebView webView;
    @FXML protected Parent root;

    public EmailForm() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/email/EmailForm.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println(e);
        }

        getStylesheets().add("http://fonts.googleapis.com/css?family=Roboto:400italic,300,700,400");

        EventBus.INSTANCE.register(this);

        initializeAccountTypeChoiceBox();
        showOrHideSendButton();
    }

    public EmailForm(String recipients, String subject, String content) {
        this();
        toTextField.setText(recipients);
        subjectTextField.setText(subject);
        contentTextArea.setText(content);
    }

    /**
     * Populates the choice box with all supported server types.
     */
    private void initializeAccountTypeChoiceBox() {
        ObservableList<IAccount> observableList = FXCollections.observableArrayList();
        for(IAccount account : accountHandler.getAccounts()) {
            observableList.add(account);
        }

        fromChoiceBox.setItems(observableList);
        fromChoiceBox.getSelectionModel().select(0);
    }

    /**
     * Parses the string in the recipients field and returns a list of IAddresses.
     *
     * @param addressString a string with addresses to parse, separated by comma
     * @return a list of parsed IAddresses
     */
    public static List<IAddress> parseAddresses(String addressString) {
        String[] addressArray = addressString.split(", ");

        List<IAddress> addressList = new ArrayList<>();
        for (String address : addressArray) {
            addressList.add(new Address(address));
        }

        return addressList;
    }

    /**
     * Depending on whether an account exists the send button is disabled or enabled.
     */
    private void showOrHideSendButton() {
        if (accountHandler.getAccounts().size() == 0) {
            this.sendButton.setDisable(true);
        } else {
            this.sendButton.setDisable(false);
        }
    }

    /**
     * Invoked when the "Insert"-button in the contact book is clicked.
     * @param contact
     */
    private void insertContactToEmail(IContact contact) {
        for (IAddress address : contact.getEmailAddresses()) {
            if(toTextField.getText().length() != 0) {
                toTextField.appendText(", " + address.getString());
            } else {
                toTextField.setText(address.getString());
            }
        }
    }

    /**
     * Invoked when typing in the content text-area with the purpose of compiling text to markdown
     * and showing it in the Web View.
     */
    @FXML
    protected void onKeyTyped() {
        try {
            html = new Markdown4jProcessor().process(contentTextArea.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }

        html = EMAIL_CSS + html;

        webView.getEngine().loadContent(html);
    }

    /**
     * Invoked when the "Send"-button is clicked.
     *
     * @param event
     */
    @FXML
    protected void sendButtonActionPerformed(ActionEvent event) {
        // Start building an email
        Email.Builder emailBuilder = new Email.Builder(subjectTextField.getText(), html);

        // Add to, cc and bcc if they are entered
        if (!this.toTextField.getText().isEmpty()) {
            List<IAddress> toAddresses = parseAddresses(this.toTextField.getText());
            emailBuilder.to(toAddresses);
        }

        if (!this.ccTextField.getText().isEmpty()) {
            List<IAddress> ccAddresses = parseAddresses(this.ccTextField.getText());
            emailBuilder.cc(ccAddresses);
        }

        if (!this.bccTextField.getText().isEmpty()) {
            List<IAddress> bccAddresses = parseAddresses(this.bccTextField.getText());
            emailBuilder.bcc(bccAddresses);
        }

        // Build the email and send it
        IEmail email = emailBuilder.build();
        fromChoiceBox.getValue().send(email);

        Stage stage = (Stage) this.getScene().getWindow();
        stage.close();
    }

    /**
     * Opens the contact book with the "ComposeEmailPresenter"-window as modal parent.
     *
     * @param event
     */
    @FXML
    protected void openContactBook(ActionEvent event) {
        Stage stage = new Stage();
        stage.setScene(new Scene(new ContactBook(true), 400, 300));
        stage.setTitle("Contact Book");
        stage.setMinWidth(400);
        stage.setMinHeight(300);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @Override
    public void onEvent(IEvent evt) {
        Platform.runLater( // JavaFX can get thread problems otherwise
                () -> handleEvent(evt)
        );
    }

    private void handleEvent(IEvent evt) {
        switch (evt.getType()) {
            case INSERT_CONTACT_TO_EMAIL:
                insertContactToEmail((IContact) evt.getValue());
                break;
        }
    }
}
