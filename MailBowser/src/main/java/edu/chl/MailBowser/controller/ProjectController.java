package edu.chl.MailBowser.controller;

import edu.chl.MailBowser.model.Project;
import edu.chl.MailBowser.view.ProjectView;

public class ProjectController {
	private final Project project;
	private final ProjectView projectView;

	public static ProjectController create(Project project, ProjectView projectView) {
		return new ProjectController(project, projectView);
	}

	private ProjectController(Project project, ProjectView projectView) {
		this.project = project;
		this.projectView = projectView;
	}
}
