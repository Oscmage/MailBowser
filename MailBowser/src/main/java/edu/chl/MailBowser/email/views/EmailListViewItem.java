package edu.chl.mailbowser.email.views;

import edu.chl.mailbowser.email.models.IEmail;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

/**
 * Created by filip on 07/05/15.
 */
public class EmailListViewItem extends AnchorPane {

    private Node view;
    private IEmail email;

    public EmailListViewItem() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EmailListViewItem.fxml"));
        try {
            view = (Node) fxmlLoader.load();
        } catch (IOException ex) {
            System.out.println("EmailListViewItem.fxml not found.");
        }
    }

    public EmailListViewItem(IEmail email) {
        super();
        this.email = email;

        ObservableList<Node> children = getChildren();

        children.add(new Label(email.getSubject()));
    }

}
