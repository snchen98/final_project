package com.capgemini.controller;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.capgemini.entity.Employee;
import com.capgemini.exceptions.ResourceAlreadyExistsException;
import com.capgemini.exceptions.ResourceNotFoundException;
import com.capgemini.service.AddressService;
import com.capgemini.service.DepartmentService;
import com.capgemini.service.EmployeeService;
import com.capgemini.service.ProjectService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.ValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(value = EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
	private MockMvc mockMvc;
	@MockBean
	private EmployeeService employeeService;
    @MockBean
    private AddressService addressService;
    @MockBean 
    private DepartmentService departmentService;
    @MockBean
    private ProjectService projectService;

    static int id;
    static String name;
    static String designation;
    static double salary;
    static Employee employee;
    static ObjectMapper mapper;
    static String expected;
    static String resourceNotFoundMsg;
    static String resourceAlreadyExistsMsg;

    @BeforeAll
    public static void init() {
        id = 123;
        name = "Bob";
        designation = "Software Engineer";
        salary = 100000;
        employee = new Employee(id, name, designation, salary, null, null, null);
        mapper = new ObjectMapper();
        resourceNotFoundMsg = "Can't find employee with id: " + id;
        resourceAlreadyExistsMsg = "Employee with id: " + id + " already exists";
    }

    @Test
    public void getAllEmployeeSuccess() throws Exception {
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(employee);
        String employeeJson = mapper.writeValueAsString(employees);
        Mockito.when(employeeService.getAllEmployee()).thenReturn(employees);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/employee")
            .contentType(MediaType.APPLICATION_JSON);
        String[] ignoreFields = new String[] {"department", "project", "address"};
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        List<Customization> customizations = new ArrayList<>(ignoreFields.length);
        for (String ignoreField : ignoreFields) {
            customizations.add(new Customization(ignoreField, new ValueMatcher<Object>() {
                @Override
                public boolean equal(Object o1, Object o2) {
                    return true;
                }
            }));
        }
        JSONAssert.assertEquals(employeeJson.replace("[", "").replace("]", ""), 
            result.getResponse().getContentAsString().replace("[", "").replace("]", ""), 
            new CustomComparator(JSONCompareMode.STRICT, customizations.toArray(new Customization[0])));
    }
    @Test
    public void getEmployeeByIdSuccess() throws Exception {
        String employeeJson = mapper.writeValueAsString(employee);
        Mockito.when(employeeService.getEmployeeById(id)).thenReturn(employee);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/employee/"+id)
            .contentType(MediaType.APPLICATION_JSON);
        String[] ignoreFields = new String[] {"department", "project", "address"};
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        List<Customization> customizations = new ArrayList<>(ignoreFields.length);
        for (String ignoreField : ignoreFields) {
            customizations.add(new Customization(ignoreField, new ValueMatcher<Object>() {
                @Override
                public boolean equal(Object o1, Object o2) {
                    return true;
                }
            }));
        }
        JSONAssert.assertEquals(employeeJson.replace("[", "").replace("]", ""), 
            result.getResponse().getContentAsString().replace("[", "").replace("]", ""), 
            new CustomComparator(JSONCompareMode.STRICT, customizations.toArray(new Customization[0])));
    }

    @Test
    public void getEmployeeByIdFail() throws Exception {
        Mockito.when(employeeService.getEmployeeById(id)).thenThrow(new ResourceNotFoundException(resourceNotFoundMsg));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/employee/"+id)
            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assertions.assertTrue(result.getResolvedException() instanceof ResourceNotFoundException);
        Assertions.assertEquals(resourceNotFoundMsg, (result.getResolvedException().getMessage()));
    }
    @Test
    public void getEmployeeByDepartmentSuccess() throws Exception {
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(employee);
        String employeeJson = mapper.writeValueAsString(employees);
        Mockito.when(employeeService.getEmployeeByDepartment(id)).thenReturn(employees);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/employee/department/"+id)
            .contentType(MediaType.APPLICATION_JSON);
        String[] ignoreFields = new String[] {"department", "project", "address"};
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        List<Customization> customizations = new ArrayList<>(ignoreFields.length);
        for (String ignoreField : ignoreFields) {
            customizations.add(new Customization(ignoreField, new ValueMatcher<Object>() {
                @Override
                public boolean equal(Object o1, Object o2) {
                    return true;
                }
            }));
        }
        JSONAssert.assertEquals(employeeJson.replace("[", "").replace("]", ""), 
            result.getResponse().getContentAsString().replace("[", "").replace("]", ""), 
            new CustomComparator(JSONCompareMode.STRICT, customizations.toArray(new Customization[0])));
    }

    @Test
    public void getEmployeeByDepartmentFail() throws Exception {
        Mockito.when(employeeService.getEmployeeByDepartment(id)).thenThrow(new ResourceNotFoundException(resourceNotFoundMsg));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/employee/department/"+id)
            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assertions.assertTrue(result.getResolvedException() instanceof ResourceNotFoundException);
        Assertions.assertEquals(resourceNotFoundMsg, (result.getResolvedException().getMessage()));
    }
    @Test
    public void getEmployeeByDesignationSuccess() throws Exception {
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(employee);
        String employeeJson = mapper.writeValueAsString(employees);
        Mockito.when(employeeService.getEmployeeByDesignation(employee.getDesignation())).thenReturn(employees);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/employee/designation/Software Engineer")
            .contentType(MediaType.APPLICATION_JSON);
        String[] ignoreFields = new String[] {"department", "project", "address"};
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        List<Customization> customizations = new ArrayList<>(ignoreFields.length);
        for (String ignoreField : ignoreFields) {
            customizations.add(new Customization(ignoreField, new ValueMatcher<Object>() {
                @Override
                public boolean equal(Object o1, Object o2) {
                    return true;
                }
            }));
        }
        JSONAssert.assertEquals(employeeJson.replace("[", "").replace("]", ""), 
            result.getResponse().getContentAsString().replace("[", "").replace("]", ""), 
            new CustomComparator(JSONCompareMode.STRICT, customizations.toArray(new Customization[0])));
    }

    @Test
    public void getEmployeeBySalarySuccess() throws Exception {
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(employee);
        String employeeJson = mapper.writeValueAsString(employees);
        Mockito.when(employeeService.getEmployeeBySalary(0, 150000)).thenReturn(employees);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/employee/salary/0/150000")
            .contentType(MediaType.APPLICATION_JSON);
        String[] ignoreFields = new String[] {"department", "project", "address"};
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        List<Customization> customizations = new ArrayList<>(ignoreFields.length);
        for (String ignoreField : ignoreFields) {
            customizations.add(new Customization(ignoreField, new ValueMatcher<Object>() {
                @Override
                public boolean equal(Object o1, Object o2) {
                    return true;
                }
            }));
        }
        JSONAssert.assertEquals(employeeJson.replace("[", "").replace("]", ""), 
            result.getResponse().getContentAsString().replace("[", "").replace("]", ""), 
            new CustomComparator(JSONCompareMode.STRICT, customizations.toArray(new Customization[0])));
    }

    @Test
    public void getEmployeeByProjectIdSuccess() throws Exception {
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(employee);
        String employeeJson = mapper.writeValueAsString(employees);
        Mockito.when(employeeService.getEmployeeByProject(id)).thenReturn(employees);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/employee/project/"+id)
            .contentType(MediaType.APPLICATION_JSON);
        String[] ignoreFields = new String[] {"department", "project", "address"};
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        List<Customization> customizations = new ArrayList<>(ignoreFields.length);
        for (String ignoreField : ignoreFields) {
            customizations.add(new Customization(ignoreField, new ValueMatcher<Object>() {
                @Override
                public boolean equal(Object o1, Object o2) {
                    return true;
                }
            }));
        }
        JSONAssert.assertEquals(employeeJson.replace("[", "").replace("]", ""), 
            result.getResponse().getContentAsString().replace("[", "").replace("]", ""), 
            new CustomComparator(JSONCompareMode.STRICT, customizations.toArray(new Customization[0])));
    }

    @Test
    public void getEmployeeByProjectIdFail() throws Exception {
        Mockito.when(employeeService.getEmployeeByProject(id)).thenThrow(new ResourceNotFoundException(resourceNotFoundMsg));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/employee/project/"+id)
            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assertions.assertTrue(result.getResolvedException() instanceof ResourceNotFoundException);
        Assertions.assertEquals(resourceNotFoundMsg, (result.getResolvedException().getMessage()));
    }
    @Test
    public void addEmployeeSuccess() throws Exception { 
        String employeeJson = mapper.writeValueAsString(employee);
        Mockito.when(employeeService.addEmployee(any(Employee.class))).thenReturn(employee);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/employee")
            .content(employeeJson)
            .contentType(MediaType.APPLICATION_JSON);
        String[] ignoreFields = new String[] {"department", "project", "address"};
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        List<Customization> customizations = new ArrayList<>(ignoreFields.length);
        for (String ignoreField : ignoreFields) {
            customizations.add(new Customization(ignoreField, new ValueMatcher<Object>() {
                @Override
                public boolean equal(Object o1, Object o2) {
                    return true;
                }
            }));
        }
        JSONAssert.assertEquals(employeeJson, result.getResponse().getContentAsString(), 
            new CustomComparator(JSONCompareMode.STRICT, customizations.toArray(new Customization[0])));
    }

    @Test
    public void addEmployeeFail() throws Exception {
        String employeeJson = mapper.writeValueAsString(employee);
        Mockito.when(employeeService.addEmployee(any(Employee.class)))
            .thenThrow(new ResourceAlreadyExistsException(resourceAlreadyExistsMsg));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/employee")
            .content(employeeJson)
            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assertions.assertTrue(result.getResolvedException() instanceof ResourceAlreadyExistsException);
        Assertions.assertEquals(resourceAlreadyExistsMsg, (result.getResolvedException().getMessage()));
    }
    @Test
    public void setEmployeeDetailsSuccess() throws Exception {
        String employeeJson = mapper.writeValueAsString(employee);
        Mockito.when(employeeService.setEmployeeDetails(any(Employee.class))).thenReturn(employee);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/employee")
            .content(employeeJson)  
            .contentType(MediaType.APPLICATION_JSON);
        String[] ignoreFields = new String[] {"department", "project", "address"};
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        List<Customization> customizations = new ArrayList<>(ignoreFields.length);
        for (String ignoreField : ignoreFields) {
            customizations.add(new Customization(ignoreField, new ValueMatcher<Object>() {
                @Override
                public boolean equal(Object o1, Object o2) {
                    return true;
                }
            }));
        }
        JSONAssert.assertEquals(employeeJson, result.getResponse().getContentAsString(), 
            new CustomComparator(JSONCompareMode.STRICT, customizations.toArray(new Customization[0])));
    }
    @Test
    public void setEmployeeDetailsFail() throws Exception {
        String employeeJson = mapper.writeValueAsString(employee);
        Mockito.when(employeeService.setEmployeeDetails(any(Employee.class)))
            .thenThrow(new ResourceAlreadyExistsException(resourceAlreadyExistsMsg));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .put("/employee")
            .content(employeeJson)
            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assertions.assertTrue(result.getResolvedException() instanceof ResourceAlreadyExistsException);
        Assertions.assertEquals(resourceAlreadyExistsMsg, (result.getResolvedException().getMessage()));
    }
    @Test
    public void deleteEmployeeByIdSuccess() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .delete("/employee/" + id).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }
    @Test
    public void deleteEmployeeByIdFail() throws Exception {
        Mockito.doThrow(new ResourceAlreadyExistsException(resourceAlreadyExistsMsg)).when(employeeService).deleteEmployeeById(id);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .delete("/employee/" + id).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Map<String, Object> jsonMap = mapper.readValue(result.getResponse().getContentAsString(), 
            new TypeReference<Map<String, Object>>(){});
        Assertions.assertEquals("409", jsonMap.get("code"));
        Assertions.assertEquals(resourceAlreadyExistsMsg, (result.getResolvedException().getMessage()));
    }
}