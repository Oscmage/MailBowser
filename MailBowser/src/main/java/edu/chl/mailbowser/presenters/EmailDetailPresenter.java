package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.email.IAddress;
import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.event.*;
import edu.chl.mailbowser.tag.ITagHandler;
import edu.chl.mailbowser.tag.ITag;
import edu.chl.mailbowser.utils.Pair;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by filip on 04/05/15.
 */

public class EmailDetailPresenter implements IObserver, Initializable {

    private ITagHandler tagHandler = MainHandler.INSTANCE.getTagHandler();

    @FXML protected Label subjectLabel;
    @FXML protected Label fromLabel;
    @FXML protected Label toLabel;
    @FXML protected Label ccLabel;
    @FXML protected Label receivedDateLabel;
    @FXML protected WebView webView;

    @FXML protected VBox emailDetail;

    @FXML private ObservableList<HBox> observableTagList = FXCollections.observableArrayList();
    @FXML protected ListView<HBox> tagListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.INSTANCE.register(this);
        tagListView.setItems(observableTagList);
        emailDetail.setOpacity(0.5);
    }

    private void updateView(IEmail email) {
        subjectLabel.setText(email.getSubject());
        receivedDateLabel.setText(email.getReceivedDate().toString());
        this.fromLabel.setText(email.getSender().getString());

        // Get strings from the receiver addresses
        List<String> receivers = email.getTo().stream()
                .map(IAddress::toString).collect(Collectors.toList());
        this.toLabel.setText(String.join(", ", receivers));

//        // ...And do the same for CC
        List<String> carbonCopies = email.getCc().stream()
                .map(IAddress::toString).collect(Collectors.toList());
        this.ccLabel.setText(String.join(", ", carbonCopies));

        this.webView.getEngine().loadContent(email.getContent());

        updateTagsList(tagHandler.getTagsWith(email));
        emailDetail.setOpacity(1);
    }

    private void updateTagsList(Set<ITag> tags) {
        observableTagList.setAll(
                tags.stream()
                        .map(TagListItemPresenter::new)
                        .collect(Collectors.toList())
        );
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

