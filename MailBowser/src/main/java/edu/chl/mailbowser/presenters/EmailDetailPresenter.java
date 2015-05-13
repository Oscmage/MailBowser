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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.web.WebView;

import java.util.Set;

/**
 * Created by filip on 04/05/15.
 */

public class EmailDetailPresenter implements IObserver {

    private Email email;

    @FXML protected Label subjectLabel;
    @FXML protected Label fromLabel;
    @FXML protected Label receivedDateLabel;
    @FXML protected Label tagLabel;
    @FXML protected WebView webView;
    @FXML protected FlowPane tagFlowPane;
    @FXML protected ListView<TagListItemPresenter> tagListView;

    public EmailDetailPresenter() {
        EventBus.INSTANCE.register(this);
    }


    private void updateView() {
        subjectLabel.setText(email.getSubject());
        receivedDateLabel.setText(email.getReceivedDate().toString());
        this.webView.getEngine().loadContent(email.getContent());
        updateTags(TagHandler.getInstance().getTags(this.email));
    }

    private void updateTags(Set<ITag> tags) {
        for (ITag tag : tags) {
            updateTags(tag);
        }
    }

    private void updateTags(ITag tag){
        ObservableList<TagListItemPresenter> observableList = tagListView.getItems();
        TagListItemPresenter p = new TagListItemPresenter(tag);

        if(!observableList.contains(p)) {
            if(observableList.size()!=0) {
                for(int i=0;i<observableList.size();i++) {
                    if (p.equals(observableList.get(i).getTag())) {
                        break;
                    }
                    if (i == observableList.size() - 1) {
                        observableList.add(p);
                    }
                }
            } else {
                observableList.add(p);
            }
        }
        this.tagListView.setItems(observableList);
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
            updateTags(((ITag) evt.getValue()));
        }
    }

}

