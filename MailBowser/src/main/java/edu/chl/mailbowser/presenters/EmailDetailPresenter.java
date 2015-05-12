package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.email.models.Email;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
import edu.chl.mailbowser.tag.handlers.TagHandler;
import edu.chl.mailbowser.tag.models.ITag;
import edu.chl.mailbowser.tag.models.Tag;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.web.WebView;

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

    public EmailDetailPresenter() {
        EventBus.INSTANCE.register(this);
    }


    private void updateView() {
        subjectLabel.setText(email.getSubject());
        receivedDateLabel.setText(email.getReceivedDate().toString());
        this.webView.getEngine().loadContent(email.getContent());
    }

    public void removeTagActionPerformed() {
        TagHandler.getInstance().removeTag(new Tag("")); // TODO changes so removeTag takes the tag.
    }

    @Override
    public void onEvent(IEvent evt) {
        switch (evt.getType()) {
            case ADD_TAG:
                tagLabel.setText(((ITag) evt.getValue()).getName());
            case REMOVE_TAG:
                //Do something
            case SELECTED_EMAIL:
                this.email = (Email) evt.getValue();
                updateView();
                break;
        }
    }

}

