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
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
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
    protected TagList<TagListItem> tagListView;

    public EmailDetail() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EmailDetailView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e.getCause());
        }

        EventBus.INSTANCE.register(this);

        tagListView = new TagList<>(TagList.Type.LOCAL);
        tagListView.setOrientation(Orientation.HORIZONTAL);
        tagListView.setEditable(false);
        VBox.setVgrow(tagListView, Priority.NEVER);
        tagListView.setId("emailDetailTagsList");
        emailDetailTop.getChildren().add(tagListView);

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

        updateTagsList(tagHandler.getTagsWithEmail(email));
        emailDetail.setOpacity(1);
    }

    /**
     * Updates the list of tags with the tags passed as parameter.
     *
     * @param tags
     */
    private void updateTagsList(Set<ITag> tags) {
        ObservableList<TagListItem> observableList = FXCollections.observableArrayList();

        for(ITag tag : tags) {
            System.out.println(tag.toString());
            TagListItem tagListItem = new TagListItem(tag, TagList.Type.LOCAL);
            tagListItem.getStyleClass().add("tag");
            observableList.add(tagListItem);
        }
        tagListView.setItems(observableList);
    }

    @Override
    public void onEvent(IEvent evt) {
        Platform.runLater( //JavaFX can get thread problems otherwise
                () -> handleEvent(evt)
        );
    }

    private void handleEvent(IEvent evt) {
        IEmail email;
        switch (evt.getType()) {
            case SELECT_EMAIL:
                updateView((IEmail)evt.getValue());
                break;
            case REMOVED_TAG_FROM_EMAIL:
                email = (IEmail)((Pair)evt.getValue()).getFirst();
                updateView(email);
                break;
            case ADDED_TAG_TO_EMAIL:
                email = (IEmail)((Pair)evt.getValue()).getFirst();
                updateView(email);
                break;
        }
    }

}

