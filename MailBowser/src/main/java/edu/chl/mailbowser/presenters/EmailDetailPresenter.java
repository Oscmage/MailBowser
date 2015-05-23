package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.email.models.Email;
import edu.chl.mailbowser.email.models.IAddress;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
import edu.chl.mailbowser.tag.handlers.ITagHandler;
import edu.chl.mailbowser.tag.models.ITag;
import edu.chl.mailbowser.tag.views.TagListItemPresenter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
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

    private Email email;
    private ITagHandler tagHandler = MainHandler.INSTANCE.getTagHandler();

    @FXML protected Label subjectLabel;
    @FXML protected Label fromLabel;
    @FXML protected Label toLabel;
    @FXML protected Label ccLabel;
    @FXML protected Label receivedDateLabel;
    @FXML protected Label tagLabel;
    @FXML protected WebView webView;
    @FXML protected FlowPane tagFlowPane;

    @FXML protected VBox emailDetail;

    @FXML private ObservableList<HBox> observableTagList = FXCollections.observableArrayList();
    @FXML protected ListView<HBox> tagListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.INSTANCE.register(this);
        tagListView.setItems(observableTagList);
        emailDetail.setOpacity(0.5);
    }

    private void updateView() {
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

        replaceListViewContent(tagHandler.getTagsWith(this.email));
        emailDetail.setOpacity(1);
    }

    private void replaceListViewContent(Set<ITag> tags) {
        observableTagList.setAll(
                tags.stream()
                        .map(TagListItemPresenter::new)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void onEvent(IEvent evt) {
        Platform.runLater(
                () -> handleEvent(evt)
        );
    }

    private void handleEvent(IEvent evt) {
        switch (evt.getType()) {
            case SELECTED_EMAIL:
                this.email = (Email) evt.getValue();
                updateView();
                break;
            case REMOVE_TAG:
                break;
            case ADD_TAG:
                replaceListViewContent(tagHandler.getTagsWith(this.email));
                break;
            case GUI_REMOVE_TAG:
                ITag tag = (ITag) evt.getValue();
                tagHandler.removeTagFromEmail(this.email, tag);
                updateView();
                break;
        }
    }

}

