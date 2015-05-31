package edu.chl.mailbowser.presenters.email;

import edu.chl.mailbowser.main.MainHandler;
import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.tag.ITag;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by filip on 07/05/15.
 */
public class EmailListItem extends FlowPane implements Comparable<EmailListItem> {

    private IEmail email;

    @FXML protected Label sender;
    @FXML protected Label sent;
    @FXML protected Label subject;
    @FXML protected Label content;
    @FXML protected Label tags;

    private EmailListItem() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/email/EmailListItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("FXML-file not found");
        }
    }

    public EmailListItem(IEmail email) {
        this();
        this.email = email;
        initializeContent();
        initializeTagsList();
    }

    /**
     * Sets sender, sent date, subject and a content excerpt to the labels on this list item.
     */
    private void initializeContent() {
        sender.setText(email.getSender().toString());
        sent.setText(new SimpleDateFormat("yyyy-MM-dd").format(email.getSentDate()));
        subject.setText(email.getSubject());
        content.setText(stripContent(email.getContent()));
    }

    /**
     * Creates a list with tags that belong to this list item's email.
     */
    private void initializeTagsList() {
        Set<String> tagStrings = new TreeSet<>();
        Set<ITag> tagSet = MainHandler.INSTANCE.getTagHandler().getTagsWithEmail(email);

        for(ITag tag : tagSet) {
            tagStrings.add(tag.getName());
        }
        tags.setText(String.join(", ", tagStrings));
    }

    /**
     * Strips the content passed as parameter from all XML-tags and line-breaks.
     * @param content
     * @return
     */
    private String stripContent(String content) {
        return content.replaceAll("<[^>]*>", "").replace("\n", "").replace("\r", "");
    }

    /**
     * Returns the email assigned to this list item.
     * @return
     */
    public IEmail getEmail() {
        return this.email;
    }

    /**
     * Compares the emails assigned to each list item and sorts it based on email received/sent date.
     * @param o
     * @return
     */
    @Override
    public int compareTo(EmailListItem o) {
        return this.email.compareTo(o.getEmail());
    }

    /**
     * Checks if the email assigned to each list item is equal and returns true if they are.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof EmailListItem) {
            return this.email.equals(
                    ((EmailListItem)o).getEmail()
            );
        }
        return false;
    }

    /**
     * Hash code is built using the email assigned to this list item.
     * @return
     */
    @Override
    public int hashCode() {
        return this.email.hashCode();
    }
}
