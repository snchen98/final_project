package com.capgemini.service;

import java.util.List;
import java.util.Optional;

import com.capgemini.entity.Project;
import com.capgemini.exceptions.ResourceAlreadyExistsException;
import com.capgemini.exceptions.ResourceNotFoundException;
import com.capgemini.repository.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService implements ProjectServiceInterface {
    @Autowired
    ProjectRepository projectRepository;

    public Project addProject(Project project) {
        if (projectRepository.findById(project.getId()).isEmpty()) {
            return projectRepository.save(project);
        }
        throw new ResourceAlreadyExistsException("Project with id: " + project.getId() + " already exists");
    }
    public List<Project> getAllProject() {
        return projectRepository.findAll();
    }
    public Project getProjectById(int id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            return project.get();
        }
        throw new ResourceNotFoundException("Can't find project with id: " + id);
    }
    public Project setProjectDetails(Project project) {
        if (projectRepository.findById(project.getId()).isPresent()) {
            return projectRepository.save(project);
        }
        throw new ResourceNotFoundException("Can't find project with id: " + project.getId());
    }
    public void deleteProjectById(int id) {
        if (projectRepository.findById(id).isPresent()) {
            projectRepository.deleteById(id);
        }
    }
}
