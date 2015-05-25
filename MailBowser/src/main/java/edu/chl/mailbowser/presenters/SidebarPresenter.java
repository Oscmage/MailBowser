package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.MainHandler;
import edu.chl.mailbowser.account.handlers.IAccountHandler;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.event.IEvent;
import edu.chl.mailbowser.event.IObserver;
import edu.chl.mailbowser.tag.handlers.ITagHandler;
import edu.chl.mailbowser.tag.models.ITag;
import edu.chl.mailbowser.tag.models.Tag;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by filip on 04/05/15.
 */
public class SidebarPresenter implements IObserver, Initializable {

    private ITagHandler tagHandler = MainHandler.INSTANCE.getTagHandler();
    private IAccountHandler accountsHandler = MainHandler.INSTANCE.getAccountHandler();

    @FXML private ListView<SidebarViewItemPresenter> tagsList;
    private ObservableList<SidebarViewItemPresenter> observableTagsList = FXCollections.observableList(new ArrayList<>());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.INSTANCE.register(this);

        observableTagsList.add(new SidebarViewItemPresenter("All emails"));

        for(ITag tag : tagHandler.getTags()) {
            observableTagsList.add(new SidebarViewItemPresenter((Tag)tag));
        }

        updateTagsList();
    }

    private void updateTagsList() {
        for(SidebarViewItemPresenter item : observableTagsList) {
            updateTagsList(item);
        }
        tagsList.setItems(observableTagsList);
    }

    private void updateTagsList(Set<ITag> tags) {
        if (!tags.isEmpty()) {
            for (ITag tag : tags) {
                updateTagsList(tag);
            }
        }
        tagsList.setItems(observableTagsList);
    }

    private void updateTagsList(SidebarViewItemPresenter item) {
        if(!observableTagsList.contains(item)) {
            observableTagsList.add(item);
        }
    }

    private void updateTagsList(ITag tag) {
        SidebarViewItemPresenter item = new SidebarViewItemPresenter((Tag)tag);
        if(!observableTagsList.contains(item)) {
            observableTagsList.add(item);
        }
        tagsList.setItems(observableTagsList);
    }

    private void deleteTag(SidebarViewItemPresenter listItem) {
        tagHandler.eraseTag(listItem.getTag());
        tagsList.getItems().remove(listItem);
    }

    @FXML
    public void addNewTag() {

    }

    /**
     * Sends an event with the chosen listItem's tag.
     * @param event
     */
    public void selectedTag(Event event) {
            EventBus.INSTANCE.publish(new edu.chl.mailbowser.event.Event(EventType.SELECT_TAG,
                    tagsList.getSelectionModel().getSelectedItem().getTag()));
    }

    @Override
    public void onEvent(IEvent evt) {
        Platform.runLater( // JavaFX can get thread problems otherwise
                () -> handleEvent(evt)
        );
    }

    private void handleEvent(IEvent evt){
        switch (evt.getType()) {
            case FETCHED_EMAIL:
                updateTagsList(tagHandler.getTagsWith((IEmail) evt.getValue()));
                break;
            case ADD_TAG_TO_EMAIL:
                updateTagsList((ITag) evt.getValue());
                break;
            case DELETE_TAG:
                deleteTag((SidebarViewItemPresenter) evt.getValue());
                break;
        }
    }

}

