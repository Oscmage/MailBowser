package edu.chl.mailbowser.presenters.email;

import edu.chl.mailbowser.main.MainHandler;
import edu.chl.mailbowser.email.IAddress;
import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.event.*;
import edu.chl.mailbowser.presenters.tag.TagList;
import edu.chl.mailbowser.presenters.tag.TagListItem;
import edu.chl.mailbowser.tag.ITagHandler;
import edu.chl.mailbowser.tag.ITag;
import edu.chl.mailbowser.utils.Pair;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by filip on 04/05/15.
 */

public class EmailDetail extends VBox implements IObserver {

    private ITagHandler tagHandler = MainHandler.INSTANCE.getTagHandler();

    @FXML protected Label subjectLabel;
    @FXML protected Label fromLabel;
    @FXML protected Label toLabel;
    @FXML protected Label ccLabel;
    @FXML protected Label receivedDateLabel;
    @FXML protected WebView webView;
    @FXML protected VBox emailDetail;
    @FXML protected VBox emailDetailTop;

    protected TagList tagList;
    private IEmail selectedEmail = null;

    public EmailDetail() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/email/EmailDetail.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e.getCause());
        }

        EventBus.INSTANCE.register(this);

        tagList = new TagList(TagList.Type.LOCAL);
        tagList.setOrientation(Orientation.HORIZONTAL);
        tagList.setEditable(false);
        VBox.setVgrow(tagList, Priority.NEVER);
        tagList.setId("emailDetailTagsList");
        emailDetailTop.getChildren().add(tagList);

        emailDetail.setOpacity(0.5);
    }

    /**
     * Updates the view based on what email is passed as parameter.
     *
     * @param email
     */
    private void updateView(IEmail email) {
        subjectLabel.setText(email.getSubject());
        receivedDateLabel.setText(email.getReceivedDate().toString());
        this.fromLabel.setText(email.getSender().getString());

        // Get strings from the receiver addresses
        List<String> receivers = email.getTo().stream()
                .map(IAddress::toString).collect(Collectors.toList());
        this.toLabel.setText(String.join(", ", receivers));

        // ...And do the same for CC
        List<String> carbonCopies = email.getCc().stream()
                .map(IAddress::toString).collect(Collectors.toList());
        this.ccLabel.setText(String.join(", ", carbonCopies));

        this.webView.getEngine().loadContent(email.getContent());

        tagList.setTags(tagHandler.getTagsWithEmail(email));
        emailDetail.setOpacity(1);
    }

    /**
     * Adds a tag to the list of tags.
     *
     * @param tag the tag to add
     */
    private void addTagToList(ITag tag) {
        tagList.addTag(tag);
    }

    /**
     * Removes a tag from the list of tags.
     *
     * @param tag the tag to remove
     */
    private void removeTagFromList(ITag tag) {
        tagList.removeTag(tag);
    }

    /**
     * Clears the tag list from all tags.
     */
    public void clearTagList() {
        tagList.clear();
    }

    /**
     * This method is invoked when a tag gets removed from an email. It updates the view only if the affected email
     * is the selected email.
     *
     * @param pair
     */
    private void removedTagFromEmail(Pair<IEmail, ITag> pair) {
        if (pair.getFirst().equals(selectedEmail)) {
            removeTagFromList(pair.getSecond());
        }
    }

    /**
     * This method is invoked when a tag gets added to an email. It updates the view only if the affected email is the
     * selected email
     *
     * @param pair
     */
    private void addedTagToEmail(Pair<IEmail, ITag> pair) {
        if (pair.getFirst().equals(selectedEmail)) {
            addTagToList(pair.getSecond());
        }
    }

    @Override
    public void onEvent(IEvent evt) {
        Platform.runLater(
                () -> handleEvent(evt)
        );
    }

    private void handleEvent(IEvent evt) {
        switch (evt.getType()) {
            case SELECT_EMAIL:
                this.selectedEmail = (IEmail) evt.getValue();
                updateView(selectedEmail);
                break;
            case REMOVED_TAG_FROM_EMAIL:
                removedTagFromEmail((Pair<IEmail, ITag>) evt.getValue());
                break;
            case ADDED_TAG_TO_EMAIL:
                addedTagToEmail((Pair<IEmail, ITag>) evt.getValue());
                break;
            case TAGS_CLEARED:
                clearTagList();
                break;
        }
    }

}

