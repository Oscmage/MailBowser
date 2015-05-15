package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.email.models.Email;
import edu.chl.mailbowser.tag.models.Tag;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by filip on 07/05/15.
 */
public class SidebarViewItemPresenter extends FlowPane implements Initializable {

    private Tag tag;

    @FXML private Label name;
    @FXML private Button button;

    public SidebarViewItemPresenter() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/sidebar/SidebarViewItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("FXML-file not found");
        }

    }

    public SidebarViewItemPresenter(Tag tag) {
        this();

        this.tag = tag;

        name.setText(tag.getName());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Tag getTag() {
        return this.tag;
    }

    public boolean equals(Object o) {
        if(o instanceof SidebarViewItemPresenter) {
            return this.tag.equals(
                    ((SidebarViewItemPresenter)o).getTag()
            );
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.tag.hashCode();
    }
}
