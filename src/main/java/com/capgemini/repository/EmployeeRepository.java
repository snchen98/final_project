package com.capgemini.repository;

import java.util.List;

import com.capgemini.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository <Employee, Integer> {
    public List<Employee> findByDepartmentId(int id);
    public List<Employee> findByDesignation(String designation);
    public List<Employee> findBySalaryBetween(double lower, double upper);
    public List<Employee> findByProjectId(int id);
}
