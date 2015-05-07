package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.email.models.Email;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
import edu.chl.mailbowser.tag.handlers.TagHandler;
import edu.chl.mailbowser.tag.models.Tag;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Created by filip on 04/05/15.
 */

public class EmailDetailPresenter implements IObserver {
    private Email email;

    @FXML protected Label emailLetterHeader;

    public EmailDetailPresenter() {
        EventBus.INSTANCE.register(this);
    }

    private void updateView() {
        emailLetterHeader.setText(email.getSubject());
    }


    public void addTagActionPerformed() {
        TagHandler.INSTANCE.addTag(this.email, new Tag("")); // TODO Change so addTag takes the specified tag.
    }

    public void removeTagActionPerformed() {
        TagHandler.INSTANCE.removeTag(new Tag("")); // TODO changes so removeTag takes the tag.
    }

    @Override
    public void onEvent(IEvent evt) {
        if (evt.getType() == EventType.ADD_TAG) {
            // TODO Present this in gui
        } else if (evt.getType() == EventType.REMOVE_TAG) {
            // TODO Present this in gui
        } else if (evt.getType() == EventType.SELECTED_EMAIL) {
            this.email = (Email) evt.getValue();
            updateView();
        }
    }

}

