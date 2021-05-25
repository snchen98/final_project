package com.capgemini.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.capgemini.entity.Address;
import com.capgemini.entity.Department;
import com.capgemini.entity.Employee;
import com.capgemini.entity.Project;
import com.capgemini.service.AddressService;
import com.capgemini.service.DepartmentService;
import com.capgemini.service.EmployeeService;
import com.capgemini.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public Employee getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }
    @GetMapping("/employee/department/{departmentId}")
    public List<Employee> getEmployeeByDepartment(@PathVariable int departmentId) {
        return employeeService.getEmployeeByDepartment(departmentId);
    }
    @GetMapping("/employee/designation/{designation}")
    public List<Employee> getEmployeeByDesignation(@PathVariable String designation) {
        return employeeService.getEmployeeByDesignation(designation);
    }
    @GetMapping("/employee/salary/{lower}/{upper}")
    public List<Employee> getEmployeeBySalary(@PathVariable double lower, @PathVariable double upper) {
        return employeeService.getEmployeeBySalary(lower, upper);
    }
    @GetMapping("/employee/project/{projectId}")
    public List<Employee> getEmployeeByProjectId(@PathVariable int projectId) {
        return employeeService.getEmployeeByProject(projectId);
    }
    @Transactional
    @PostMapping("/employee")
    public Employee addEmployee(@RequestBody @Valid Employee employee) {
        checkIfEmployeeDetailsExist(employee);
        return employeeService.addEmployee(employee);
    }
    @Transactional
    @PutMapping("/employee")
    public Employee setEmployeeDetails(@RequestBody @Valid Employee employee) {
        checkIfEmployeeDetailsExist(employee);
        return employeeService.setEmployeeDetails(employee);
    }
    @DeleteMapping("/employee/{id}")
    public void deleteEmployeeById(@PathVariable int id) {
        employeeService.deleteEmployeeById(id);
    }

    /*
        Address, department, project can be null for employee
        Can only add a department to an employee if the department exists
        Same for project
        Address is created when employee is created
    */
    private void checkIfEmployeeDetailsExist(Employee employee) {
        Optional<Address> address = employee.getAddress();
        Optional<Department> department = employee.getDepartment();
        Optional<Project> project = employee.getProject();
        // Check if the referenced department exists
        if (department.isPresent()) {
            // This method will throw ResourceNotFoundException if department does not exist
            departmentService.getDepartmentById(department.get().getId());
        }
         // Check if the referencel project exists
        if (project.isPresent()) {
            // This method will throw ResourceNotFoundException if project does not exist
            projectService.getProjectById(project.get().getId());
        }
        if (address.isPresent()) {
            // Check if employee has an address already
            addressService.addAddress(address.get());
        }
    }
}
