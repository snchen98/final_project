package com.capgemini.service;

import java.util.List;
import java.util.Optional;

import com.capgemini.entity.Project;

public interface ProjectServiceInterface {
    public Project addProject(Project project);
    public List<Project> getAllProject();
    public Optional<Project> getProjectById(int id);
    public Project setProjectDetails(Project project);
    public void deleteProjectById(int id);
}
