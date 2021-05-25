package com.capgemini.service;

import java.util.List;
import java.util.Optional;

import com.capgemini.entity.Department;
import com.capgemini.exceptions.ResourceAlreadyExistsException;
import com.capgemini.exceptions.ResourceNotFoundException;
import com.capgemini.repository.DepartmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService implements DepartmentServiceInterface {

    @Autowired
    DepartmentRepository departmentRepository;

    public Department addDepartment(Department department) {
        if (!departmentRepository.findById(department.getId()).isPresent()) {
            return departmentRepository.save(department);
        }
        throw new ResourceAlreadyExistsException("Department with id :" + department.getId() + " already exists");
    }
    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }
    public Department getDepartmentById(int id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            return department.get();
        }
        throw new ResourceNotFoundException("can't find the department with id: " + id);
    }
    public Department setDepartmentDetails(Department department) {
        if (departmentRepository.findById(department.getId()).isPresent()) {
            return departmentRepository.save(department);
        }
        throw new ResourceNotFoundException("Can't find the department with id: " + department.getId());
    }
    public void deleteDepartmentbyId(int id) {
        if (departmentRepository.findById(id).isPresent()) {
            departmentRepository.deleteById(id);
            return;
        }
        throw new ResourceNotFoundException("Deparment with id: " + id + " does not exist in database");
    }
}
