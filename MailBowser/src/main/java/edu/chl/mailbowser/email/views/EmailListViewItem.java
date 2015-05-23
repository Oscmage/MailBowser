package edu.chl.mailbowser.email.views;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.tag.models.ITag;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

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
        sent.setText(new SimpleDateFormat("yyyy-MM-dd").format(email.getSentDate()));
        subject.setText(email.getSubject());
        content.setText(email.getContent().replaceAll("<[^>]*>", "").replace("\n", "").replace("\r", ""));

        List<String> tagStrings = new ArrayList<String>();
        Set<ITag> tagSet = MainHandler.INSTANCE.getTagHandler().getTagsWith(email);
        for(ITag tag : tagSet) {
            tagStrings.add(tag.getName());
        }
        tags.setText(String.join(", ", tagStrings));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public IEmail getEmail() {
        return this.email;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof EmailListViewItem) {
            EmailListViewItem e = (EmailListViewItem) o;
            return this.getEmail().compareTo(e.getEmail());
        }
        return 0;
    }
    public boolean equals(Object o) {
        if(o instanceof EmailListViewItem) {
            return this.email.equals(
                    ((EmailListViewItem)o).getEmail()
            );
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.email.hashCode();
    }
}
