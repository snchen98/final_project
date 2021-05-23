package com.capgemini.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.capgemini.entity.Address;
import com.capgemini.entity.Department;
import com.capgemini.entity.Employee;
import com.capgemini.entity.Project;
import com.capgemini.exceptions.ResourceNotFoundException;
import com.capgemini.service.AddressService;
import com.capgemini.service.DepartmentService;
import com.capgemini.service.EmployeeService;
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
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    AddressService addressService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    ProjectService projectService;

    @GetMapping("/employee")
    public List<Employee> getAllEmployee() {
        return employeeService.getAllEmployee();
    }
    @GetMapping("/employee/{id}")
    public Optional<Employee> getEmployeeById(@RequestParam int id) {
        return employeeService.getEmployeeById(id);
    }
    @GetMapping("/employee/{departmentId}")
    public List<Employee> getEmployeeByDepartment(@RequestParam int departmentId) {
        return employeeService.getEmployeeByDepartment(departmentId);
    }
    @GetMapping("/employee/{designation}")
    public List<Employee> getEmployeeByDesignation(@RequestParam String designation) {
        return employeeService.getEmployeeByDesignation(designation);
    }
    @GetMapping("/employee/{lower}/{upper}")
    public List<Employee> getEmployeeBySalary(@RequestParam double lower, double upper) {
        return employeeService.getEmployeeBySalary(lower, upper);
    }
    @GetMapping("/employee/{projectId}")
    public List<Employee> getEmployeeByProjectId(@RequestParam int projectId) {
        return employeeService.getEmployeeByProject(projectId);
    }
    @Transactional
    @PostMapping("/employee")
    public Employee addEmployee(@RequestBody @Valid Employee employee) {
        checkIfEmployeeDetailsExist(employee);
        return employeeService.addEmployee(employee);
    }
    @PutMapping("/employee")
    public Employee setEmployeeDetails(@RequestBody @Valid Employee employee) {
        checkIfEmployeeDetailsExist(employee);
        return employeeService.setEmployeeDetails(employee);
    }
    @DeleteMapping("/employee/{id}")
    public void deleteEmployeeById(@RequestParam int id) {
        employeeService.deleteEmployeeById(id);
    }

    /*
        Address, department, project can be null for employee
        Can only add a department to an employee if the department exists
        Same for project
        Address is created when employee is created
    */
    private void checkIfEmployeeDetailsExist(Employee employee) {
        if (employee.getDepartment().isPresent()) {
            System.out.println("DEPTID: " + employee.getDepartment().get().getId());
        }
        Optional<Address> address = employee.getAddress();
        Optional<Department> department = employee.getDepartment();
        Optional<Project> project = employee.getProject();
        if (department.isPresent()) {
            Optional<Department> dbDepartment = departmentService.getDepartmentById(department.get().getId());
            System.out.println("????" +department.get().getId());
            if (dbDepartment.isEmpty()) {
                throw new ResourceNotFoundException("Department does not exist");
            }
        }
        if (project.isPresent()) {
            Optional<Project> dbProject = projectService.getProjectById(project.get().getId());
            if (dbProject.isEmpty()) {
                throw new ResourceNotFoundException("Project does not exist");
            }
        }
        if (address.isPresent()) {
            addressService.addAdress(address.get());
        }
    }
}
