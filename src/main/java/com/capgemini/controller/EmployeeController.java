package com.capgemini.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.capgemini.entity.Employee;
import com.capgemini.service.EmployeeService;

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
    @PostMapping("/employee")
    public Employee addEmployee(@RequestBody @Valid Employee employee) {
        return employeeService.addEmployee(employee);
    }
    @PutMapping("/employee")
    public Employee setEmployeeDetails(@RequestBody @Valid Employee employee) {
        return employeeService.setEmployeeDetails(employee);
    }
    @DeleteMapping("/employee/{id}")
    public void deleteEmployeeById(@RequestParam int id) {
        employeeService.deleteEmployeeById(id);
    }
}
