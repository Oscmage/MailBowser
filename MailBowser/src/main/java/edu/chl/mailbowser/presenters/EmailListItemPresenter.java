package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.main.MainHandler;
import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.tag.ITag;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by filip on 07/05/15.
 */
public class EmailListItemPresenter extends FlowPane implements Initializable, Comparable {

    private IEmail email;

    @FXML protected Label sender;
    @FXML protected Label sent;
    @FXML protected Label subject;
    @FXML protected Label content;
    @FXML protected Label tags;

    public EmailListItemPresenter() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EmailListViewItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("FXML-file not found");
        }

    }

    public EmailListItemPresenter(IEmail email) {
        this();

        this.email = email;


        sender.setText(email.getSender().toString());
        sent.setText(new SimpleDateFormat("yyyy-MM-dd").format(email.getSentDate()));
        subject.setText(email.getSubject());
        content.setText(email.getContent().replaceAll("<[^>]*>", "").replace("\n", "").replace("\r", ""));

        Set<String> tagStrings = new TreeSet<>();
        Set<ITag> tagSet = MainHandler.INSTANCE.getTagHandler().getTagsWithEmail(email);
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
        if (o instanceof EmailListItemPresenter) {
            EmailListItemPresenter e = (EmailListItemPresenter) o;
            return this.getEmail().compareTo(e.getEmail());
        }
        return 0;
    }
    public boolean equals(Object o) {
        if(o instanceof EmailListItemPresenter) {
            return this.email.equals(
                    ((EmailListItemPresenter)o).getEmail()
            );
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.email.hashCode();
    }
}
