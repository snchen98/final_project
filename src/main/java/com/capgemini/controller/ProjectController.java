package com.capgemini.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.capgemini.entity.Project;
import com.capgemini.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @GetMapping("/project")
    public List<Project> getAllProject() {
        return projectService.getAllProject();
    }
    @GetMapping("/project/{id}")
    public Optional<Project> getProjectById(@RequestParam int id) {
        return projectService.getProjectById(id);
    }
    @PostMapping("/project")
    public Project addProject(@RequestBody @Valid Project project) {
        return projectService.addProject(project);
    }
    @PutMapping("/project")
    public Project setProjectDetails(@RequestBody @Valid Project project) {
        return projectService.setProjectDetails(project);
    }
    @DeleteMapping("/project/{id}")
    public void deleteProjectById(@RequestParam int id) {
        projectService.deleteProjectById(id);
    }
}
