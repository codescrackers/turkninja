package com.yazilimokulu.mvc.data.services;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.yazilimokulu.mvc.data.entities.Project;
import com.yazilimokulu.mvc.data.entities.Sponsor;

public class ProjectService {

	private List<Project> projects = new LinkedList<>();

	public ProjectService() {
		Project javaProject = this.createProject(1L, "Java Project",
				"This is a Java Project", new Sponsor("Oracle", "555-555-5555",
						"oracle@oracle.com"));
		Project javascriptProject = this.createProject(2L,
				"Javascript Project", "This is a Javascript Project",
				new Sponsor("Mozilla", "555-555-5555", "mozilla@mozilla.com"));
		Project htmlProject = this.createProject(3L, "HTML Project",
				"This is an HTML project", new Sponsor("Google",
						"555-555-5555", "google@google.com"));

		this.projects.addAll(Arrays.asList(new Project[] { javaProject,
				javascriptProject, htmlProject }));
	}

	public List<Project> findAll() {
		return this.projects;
	}

	public Project find(Long projectId) {
		return this.projects.stream().filter(p -> {
			return p.getProjectId().equals(projectId);
		}).collect(Collectors.toList()).get(0);
	}
	
	public void save(Project project){
		this.projects.add(project);
	}

	private Project createProject(Long projectId, String title,
			String description, Sponsor sponsor) {
		Project project = new Project();
		project.setName(title);
		project.setAuthorizedFunds(new BigDecimal("100000"));
		project.setAuthorizedHours(new BigDecimal("1000"));
		project.setProjectId(projectId);
		project.setSpecial(false);
		project.setType("multi");
		project.setYear("2015");
		project.setDescription(description);
		project.setSponsor(sponsor);
		return project;
	}
	

}
