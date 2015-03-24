package edu.chl.MailBowser.view;

import edu.chl.MailBowser.model.Project;
import javax.swing.JFrame;

public class ProjectView extends JFrame {

    public ProjectView(Project project) {
        super(Project.PROJECT_WINDOW_TEXT);
    }
}
