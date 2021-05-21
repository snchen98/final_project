package com.capgemini.service;

import java.util.List;
import java.util.Optional;

import com.capgemini.entity.Project;
import com.capgemini.repository.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService implements ProjectServiceInterface {
    @Autowired
    ProjectRepository projectRepository;

    public Project addProject(Project project) {
        return projectRepository.save(project);
    }
    public List<Project> getAllProject() {
        return projectRepository.findAll();
    }
    public Optional<Project> getProjectById(int id) {
        return projectRepository.findById(id);
    }
    public Project setProjectDetails(Project project) {
        return projectRepository.save(project);
    }
    public void deleteProjectById(int id) {
        if (projectRepository.findById(id).isPresent()) {
            projectRepository.deleteById(id);
        }
    }
}
