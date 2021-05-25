package com.capgemini.controller;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.capgemini.entity.Employee;
import com.capgemini.entity.Project;
import com.capgemini.exceptions.ResourceAlreadyExistsException;
import com.capgemini.exceptions.ResourceNotFoundException;
import com.capgemini.service.DepartmentService;
import com.capgemini.service.ProjectService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@WebMvcTest(value = ProjectController.class)
public class ProjectControllerTest {
    @Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProjectService projectService;

    static int id;
    static String name;
    static String description;
    static List<Employee> employees;
    static Project project;
    static ObjectMapper mapper;
    static String expected;
    static String resourceNotFoundMsg;
    static String resourceAlreadyExistsMsg;

    @BeforeAll
    public static void init() {
        id = 123;
        name = "Project 8270";
        description = "Top secret";
        employees = new ArrayList<Employee>();
        project = new Project(id, name, description, employees);
        mapper = new ObjectMapper();
        expected = String.format("{id:%d,name:\"%s\",description:\"%s\",employees:[]}", id, name, description, employees);
        resourceNotFoundMsg = "Can't find project with id: " + id;
        resourceAlreadyExistsMsg = "Project with id: " + id + " already exists";
    }
    @Test
    public void getAllProjectSuccess() throws Exception {
        List<Project> ProjectL = new ArrayList<Project>();
        ProjectL.add(project);
        Mockito.when(projectService.getAllProject()).thenReturn(ProjectL);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/project")
            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        JSONAssert.assertEquals("["+expected+"]", result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getProjectByIdSuccess() throws Exception {
        Mockito.when(projectService.getProjectById(id)).thenReturn(project);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/project/"+id)
            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getProjectByIdFail() throws Exception {
        Mockito.when(projectService.getProjectById(id)).thenThrow(new ResourceNotFoundException(resourceNotFoundMsg));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/project/"+id)
            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assertions.assertTrue(result.getResolvedException() instanceof ResourceNotFoundException);
        Assertions.assertEquals(resourceNotFoundMsg, (result.getResolvedException().getMessage()));
    }

    @Test
    public void addProjectSucess() throws Exception {
        String projectJson = mapper.writeValueAsString(project);
        Mockito.when(projectService.addProject(any(Project.class))).thenReturn(project);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/project")
            .content(projectJson)
            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }
    
    @Test
    public void addProjectFail() throws Exception {
        String projectJson = mapper.writeValueAsString(project);
        Mockito.when(projectService.addProject(any(Project.class))).thenThrow(new ResourceAlreadyExistsException(resourceAlreadyExistsMsg));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/project")
            .content(projectJson)
            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assertions.assertTrue(result.getResolvedException() instanceof ResourceAlreadyExistsException);
        Assertions.assertEquals(resourceAlreadyExistsMsg, (result.getResolvedException().getMessage()));
    }

    @Test
    public void setProjectDetailsSuccess() throws Exception {
        String projectJson = mapper.writeValueAsString(project);
        Mockito.when(projectService.setProjectDetails(any(Project.class))).thenReturn(project);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/project")
            .content(projectJson)
            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        JSONAssert.assertEquals(projectJson, result.getResponse().getContentAsString(), false);
    }
 
    @Test
    public void setProjectDetailsFail() throws Exception {
        String projectJson = mapper.writeValueAsString(project);
        Mockito.when(projectService.setProjectDetails(any(Project.class))).thenThrow(new ResourceAlreadyExistsException(resourceAlreadyExistsMsg));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/project")
            .content(projectJson)
            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assertions.assertTrue(result.getResolvedException() instanceof ResourceAlreadyExistsException);
        Assertions.assertEquals(resourceAlreadyExistsMsg, (result.getResolvedException().getMessage()));
    }

    @Test
    public void deleteProjectByIdSuccess() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .delete("/project/" + id).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void deleteProjectByIdFail() throws Exception {
        Mockito.doThrow(new ResourceAlreadyExistsException(resourceAlreadyExistsMsg)).when(projectService).deleteProjectById(id);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .delete("/project/" + id).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Map<String, Object> jsonMap = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Map<String, Object>>(){});
        Assertions.assertEquals("409", jsonMap.get("code"));
        Assertions.assertEquals(resourceAlreadyExistsMsg, (result.getResolvedException().getMessage()));
    }
}
