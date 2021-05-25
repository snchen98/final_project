package com.capgemini.service;

import java.util.List;
import java.util.Optional;

import com.capgemini.entity.Employee;
import com.capgemini.exceptions.ResourceAlreadyExistsException;
import com.capgemini.exceptions.ResourceNotFoundException;
import com.capgemini.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements EmployeeServiceInterface{
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    AddressService addressService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    ProjectService projectService;

    public Employee addEmployee(Employee employee) {
        if (employeeRepository.findById(employee.getId()).isEmpty()) {
            return employeeRepository.save(employee);
        }
        throw new ResourceAlreadyExistsException("Employee with id: " + employee.getId() + " already exists");
    }
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }
    public Employee getEmployeeById(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        }
        throw new ResourceNotFoundException("Can't find employee with id: " + id);
    }
    public List<Employee> getEmployeeByDepartment(int departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }
    public List<Employee> getEmployeeByDesignation(String designation) {
        return employeeRepository.findByDesignation(designation);
    }
    public List<Employee> getEmployeeBySalary(double lower, double upper) {
        return employeeRepository.findBySalaryBetween(lower, upper);
    }
    public List<Employee> getEmployeeByProject(int projectId) {
        return employeeRepository.findByProjectId(projectId);
    }
    public Employee setEmployeeDetails(Employee employee) {
        if (employeeRepository.findById(employee.getId()).isPresent()) {
            return employeeRepository.save(employee); 
        }
        throw new ResourceNotFoundException("Can't find employee with id: " + employee.getId());
    }
    public void deleteEmployeeById(int id) {
        if (employeeRepository.findById(id).isPresent()) {
            employeeRepository.deleteById(id);
        }
        throw new ResourceNotFoundException("Employee with id: " + id + " does not exist in database");
    }
}
