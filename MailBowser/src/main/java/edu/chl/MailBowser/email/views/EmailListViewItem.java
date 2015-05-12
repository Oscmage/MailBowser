package edu.chl.mailbowser.email.views;

import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.tag.handlers.TagHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by filip on 07/05/15.
 */
public class EmailListViewItem extends FlowPane implements Initializable, Comparable {

    private IEmail email;

    @FXML private Label sender;
    @FXML private Label sent;
    @FXML private Label subject;
    @FXML private Label content;
    @FXML private Label tags;

    public EmailListViewItem() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EmailListViewItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("FXML-file not found");
        }

    }

    public EmailListViewItem(IEmail email) {
        this();

        this.email = email;

        sender.setText(email.getSender().toString());
        sent.setText(email.getSentDate().toString());
        subject.setText(email.getSubject());
        content.setText(email.getContent());
        // tags.setText(TagHandler.INSTANCE.getTags(email).toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public IEmail getEmail() {
        return this.email;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof EmailListViewItem){
            EmailListViewItem e = (EmailListViewItem) o;
            return this.getEmail().compareTo(e);
        }
        return 0;
    }
}
