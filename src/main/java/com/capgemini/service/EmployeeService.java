package com.capgemini.service;

import java.util.List;
import java.util.Optional;

import com.capgemini.entity.Address;
import com.capgemini.entity.Department;
import com.capgemini.entity.Employee;
import com.capgemini.entity.Project;
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
        Optional<Address> address = employee.getAddress();
        Optional<Department> department = employee.getDepartment();
        Optional<Project> project = employee.getProject();
        if (department.isPresent()) {
            Optional<Department> dbDepartment = departmentService.getDepartmentById(department.get().getId());
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
        return employeeRepository.save(employee);
    }
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }
    public Optional<Employee> getEmployeeById(int id) {
        return employeeRepository.findById(id);
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
        return employeeRepository.save(employee);
    }
    public void deleteEmployeeById(int id) {
        if (employeeRepository.findById(id).isPresent()) {
            employeeRepository.deleteById(id);
        }
    }
}
