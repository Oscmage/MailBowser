package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.account.handlers.AccountHandler;
import edu.chl.mailbowser.email.models.Address;
import edu.chl.mailbowser.email.models.IAddress;
import edu.chl.mailbowser.email.models.Email;
import edu.chl.mailbowser.email.models.IEmail;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.markdown4j.Markdown4jProcessor;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by mats on 09/04/15.
 */
public class ComposeEmailPresenter extends GridPane implements Initializable {

    // Assign the fields from the view to variables via the fx:id attribute
    // Note that these variables belong to the javafx.scene.control package
    @FXML private TextField receiver;
    @FXML private TextField subject;
    @FXML private TextArea content;
    @FXML private WebView markdown;

    public ComposeEmailPresenter(String recipient, String subject, String content) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ComposeEmailView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println(e);
        }

        getStylesheets().add("http://fonts.googleapis.com/css?family=Roboto:400italic,300,700,400");

        setReceiver(recipient);
        setSubject(subject);
        setContent(content);
    }

    // This method is invoked when the send button is pressed, and is bound via the onAction attribute
    @FXML protected void sendButtonActionPerformed(ActionEvent event) {

        // Declare receivers
        List<IAddress> receivers = new ArrayList<IAddress>();
        receivers.add(new Address(this.receiver.getText()));

        // Create a new email and send it
        IEmail email = new Email(receivers, this.subject.getText(), this.content.getText());
        AccountHandler.getInstance().getAccount().send(email);
    }

    public void setReceiver(String value) {
        receiver.textProperty().set(value);
    }

    public void setSubject(String value) {
        subject.textProperty().set(value);
    }

    public void setContent(String value) {
        content.textProperty().set(value);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onKeyTyped() {
        String html = null;
        try {
            html = new Markdown4jProcessor().process(content.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        markdown.getEngine().loadContent(html);
    }
}
