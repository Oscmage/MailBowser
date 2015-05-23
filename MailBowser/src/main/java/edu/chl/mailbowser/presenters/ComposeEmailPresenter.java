package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.account.handlers.IAccountHandler;
import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.email.models.Address;
import edu.chl.mailbowser.email.models.Email;
import edu.chl.mailbowser.email.models.IAddress;
import edu.chl.mailbowser.email.models.IEmail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.markdown4j.Markdown4jProcessor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by mats on 09/04/15.
 */
public class ComposeEmailPresenter extends GridPane implements Initializable {
    private static final String EMAIL_CSS = "<head><style>* {font-family: \"Arial\"}</style></head>";

    private IAccountHandler accountHandler = MainHandler.INSTANCE.getAccountHandler();

    private String html = "";

    // Assign the fields from the view to variables via the fx:id attribute
    // Note that these variables belong to the javafx.scene.control package
    @FXML protected TextField to;
    @FXML protected TextField cc;
    @FXML protected TextField bcc;
    @FXML protected TextField subject;
    @FXML protected TextArea content;
    @FXML protected WebView markdown;
    @FXML protected Parent root;

    public ComposeEmailPresenter(String recipients, String subject, String content) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ComposeEmailView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println(e);
        }

        getStylesheets().add("http://fonts.googleapis.com/css?family=Roboto:400italic,300,700,400");

        setReceivers(recipients);
        setSubject(subject);
        setContent(content);

    }

    // This method is invoked when the send button is pressed, and is bound via the onAction attribute
    @FXML protected void sendButtonActionPerformed(ActionEvent event) {
        // Start building an email
        Email.Builder emailBuilder = new Email.Builder(subject.getText(), html);

        // Add to, cc and bcc if they are entered
        if (!this.to.getText().isEmpty()) {
            List<IAddress> toAddresses = parseAddresses(this.to.getText());
            emailBuilder.to(toAddresses);
        }

        if (!this.cc.getText().isEmpty()) {
            List<IAddress> ccAddresses = parseAddresses(this.cc.getText());
            emailBuilder.cc(ccAddresses);
        }

        if (!this.bcc.getText().isEmpty()) {
            List<IAddress> bccAddresses = parseAddresses(this.bcc.getText());
            emailBuilder.bcc(bccAddresses);
        }

        // Build the email and send it
        IEmail email = emailBuilder.build();

        // TODO: Fix sender
        accountHandler.getAccounts().get(0).send(email);
    }

    @FXML protected void openContactBook(ActionEvent event) {

    }

    public void setReceivers(String value) {
        to.textProperty().set(value);
    }

    public void setSubject(String value) {
        subject.textProperty().set(value);
    }

    public void setContent(String value) {
        content.textProperty().set(value);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onKeyTyped() {
        try {
            html = new Markdown4jProcessor().process(content.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }

        html = EMAIL_CSS + html;

        markdown.getEngine().loadContent(html);
    }
}
