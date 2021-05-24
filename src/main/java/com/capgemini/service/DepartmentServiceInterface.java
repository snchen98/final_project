package com.capgemini.service;

import java.util.List;

import com.capgemini.entity.Department;

public interface DepartmentServiceInterface {
    public Department addDepartment(Department department);
    public List<Department> getAllDepartment();
    public Department getDepartmentById(int id);
    public Department setDepartmentDetails(Department department);
    public void deleteDepartmentbyId(int id);
}
