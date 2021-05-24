package com.capgemini.service;

import java.util.List;

import com.capgemini.entity.Employee;

public interface EmployeeServiceInterface {
    public Employee addEmployee(Employee employee);
    public List<Employee> getAllEmployee();
    public Employee getEmployeeById(int id);
    public List<Employee> getEmployeeByDepartment(int departmentId);
    public List<Employee> getEmployeeByDesignation(String designation);
    public List<Employee> getEmployeeBySalary(double lower, double upper);
    public List<Employee> getEmployeeByProject(int projectId);
    public Employee setEmployeeDetails(Employee employee);
    public void deleteEmployeeById(int id);
}
