package com.capgemini.controller;

import java.util.List;

import javax.validation.Valid;

import com.capgemini.entity.Department;
import com.capgemini.service.DepartmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @GetMapping("/department")
    public List<Department> getAllDepartment() {
        return departmentService.getAllDepartment();
    }
    @GetMapping("/department/{id}")
    public Department getDepartmentById(@PathVariable int id) {
        return departmentService.getDepartmentById(id);
    }
    @PostMapping("/department")
    public Department addDepartment(@RequestBody @Valid Department department) {
        return departmentService.addDepartment(department);
    }
    @PutMapping("/department")
    public Department setDepartmentDetails(@RequestBody @Valid Department department) {
        return departmentService.setDepartmentDetails(department);
    }
    @DeleteMapping("/department/{id}")
    public void deleteDepartmentById(@PathVariable int id) {
        departmentService.deleteDepartmentbyId(id);
    }
}
