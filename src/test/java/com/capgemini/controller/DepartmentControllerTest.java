package com.capgemini.controller;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.capgemini.entity.Department;
import com.capgemini.entity.Employee;
import com.capgemini.exceptions.ResourceAlreadyExistsException;
import com.capgemini.exceptions.ResourceNotFoundException;
import com.capgemini.service.DepartmentService;
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

@WebMvcTest(value = DepartmentController.class)
public class DepartmentControllerTest {
    @Autowired
	private MockMvc mockMvc;

	@MockBean
	private DepartmentService departmentService;

    static int id;
    static String title;
    static String description;
    static List<Employee> employees;
    static Department department;
    static ObjectMapper mapper;
    static String expected;
    static String resourceNotFoundMsg;
    static String resourceAlreadyExistsMsg;

    @BeforeAll
    public static void init() {
        id = 123;
        title = "HR";
        description = "Human Resources";
        employees = new ArrayList<Employee>();
        department = new Department(id, title, description, employees);
        mapper = new ObjectMapper();
        expected = String.format("{id:%d,title:\"%s\",description:\"%s\",employees:[]}", id, title, description, employees);
        resourceNotFoundMsg = "Can't find department with id: " + id;
        resourceAlreadyExistsMsg = "Department with id: " + id + " already exists";
    }

    @Test
    public void getAllDepartmentSuccess() throws Exception {
        List<Department> departmentL = new ArrayList<Department>();
        departmentL.add(department);
        Mockito.when(departmentService.getAllDepartment()).thenReturn(departmentL);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/department")
            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        JSONAssert.assertEquals("["+expected+"]", result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getDepartmentByIdSuccess() throws Exception {
        Mockito.when(departmentService.getDepartmentById(id)).thenReturn(department);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/department/"+id)
            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getDepartmentByIdFail() throws Exception {
        Mockito.when(departmentService.getDepartmentById(id)).thenThrow(new ResourceNotFoundException(resourceNotFoundMsg));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/department/"+id)
            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assertions.assertTrue(result.getResolvedException() instanceof ResourceNotFoundException);
        Assertions.assertEquals(resourceNotFoundMsg, (result.getResolvedException().getMessage()));
    }
    
    @Test
    public void addDepartmentSuccess() throws Exception {
        String departmentJson = mapper.writeValueAsString(department);
        Mockito.when(departmentService.addDepartment(any(Department.class))).thenReturn(department);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/department")
            .content(departmentJson)
            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void addDepartmentFail() throws Exception {
        String departmentJson = mapper.writeValueAsString(department);
        Mockito.when(departmentService.addDepartment(any(Department.class))).thenThrow(new ResourceAlreadyExistsException(resourceAlreadyExistsMsg));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/department")
            .content(departmentJson)
            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assertions.assertTrue(result.getResolvedException() instanceof ResourceAlreadyExistsException);
        Assertions.assertEquals(resourceAlreadyExistsMsg, (result.getResolvedException().getMessage()));
    }
    
    @Test
    public void setDepartmentDetailsSuccess() throws Exception {
        String departmentJson = mapper.writeValueAsString(department);
        Mockito.when(departmentService.setDepartmentDetails(any(Department.class))).thenReturn(department);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/department")
            .content(departmentJson)
            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        JSONAssert.assertEquals(departmentJson, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void setDepartmentDetailsFail() throws Exception {
        String departmentJson = mapper.writeValueAsString(department);
        Mockito.when(departmentService.setDepartmentDetails(any(Department.class))).thenThrow(new ResourceAlreadyExistsException(resourceAlreadyExistsMsg));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/department")
            .content(departmentJson)
            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assertions.assertTrue(result.getResolvedException() instanceof ResourceAlreadyExistsException);
        Assertions.assertEquals(resourceAlreadyExistsMsg, (result.getResolvedException().getMessage()));
    }
    
    @Test
    public void deleteDepartmentByIdSuccess() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
        .delete("/department/" + id).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void deleteDepartmentByIdFail() throws Exception {
        Mockito.doThrow(new ResourceAlreadyExistsException(resourceAlreadyExistsMsg)).when(departmentService).deleteDepartmentbyId(id);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .delete("/department/" + id).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Map<String, Object> jsonMap = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Map<String, Object>>(){});
        Assertions.assertEquals("409", jsonMap.get("code"));
        Assertions.assertEquals(resourceAlreadyExistsMsg, (result.getResolvedException().getMessage()));
    }
}
