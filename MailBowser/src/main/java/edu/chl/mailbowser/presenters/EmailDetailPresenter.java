package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.email.models.Email;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
import edu.chl.mailbowser.tag.handlers.TagHandler;
import edu.chl.mailbowser.tag.models.ITag;
import edu.chl.mailbowser.tag.models.Tag;
import edu.chl.mailbowser.tag.views.TagListItemPresenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by filip on 04/05/15.
 */

public class EmailDetailPresenter implements IObserver, Initializable {

    private Email email;

    @FXML protected Label subjectLabel;
    @FXML protected Label fromLabel;
    @FXML protected Label receivedDateLabel;
    @FXML protected Label tagLabel;
    @FXML protected WebView webView;
    @FXML protected FlowPane tagFlowPane;

    @FXML private ObservableList<TagListItemPresenter> observableTagList = FXCollections.observableArrayList();
    @FXML protected ListView<TagListItemPresenter> tagListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.INSTANCE.register(this);
        tagListView.setItems(observableTagList);
    }

    private void updateView() {
        subjectLabel.setText(email.getSubject());
        receivedDateLabel.setText(email.getReceivedDate().toString());
        this.fromLabel.setText(email.getSender().getString());

        this.webView.getEngine().loadContent(email.getContent());

        replaceListViewContent(TagHandler.getInstance().getTags(this.email));
    }

    private void replaceListViewContent(Set<ITag> tags) {
        observableTagList.setAll(
                tags.stream()
                        .map(TagListItemPresenter::new)
                        .collect(Collectors.toList())
        );
    }

    public void removeTagActionPerformed() {
        TagHandler.getInstance().removeTag(new Tag("")); // TODO changes so removeTag takes the tag.
    }

    @Override
    public void onEvent(IEvent evt) {
        if(evt.getType() == EventType.SELECTED_EMAIL) {
            this.email = (Email) evt.getValue();
            updateView();
        } else if (evt.getType() == EventType.REMOVE_TAG) {
            // something
        } else if (evt.getType() == EventType.ADD_TAG){
            replaceListViewContent(TagHandler.getInstance().getTags(this.email));
        }
    }

}

