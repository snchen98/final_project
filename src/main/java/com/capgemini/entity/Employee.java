package com.capgemini.entity;


import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
@Entity
@Transactional
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String designation;
    private double salary;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "address_id")
    private Address address;

    public Employee() {
        super();
    }

    public Employee(int id, String name, String designation, double salary, Department department, Project project, Address address) {
        super();
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.salary = salary;
        this.department = department;
        this.project = project;
        this.address = address;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getSalary() {
        return this.salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Optional<Department> getDepartment() {
        return Optional.ofNullable(this.department);
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Optional<Project> getProject() {
        return Optional.ofNullable(this.project);
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Optional<Address> getAddress() {
        return Optional.ofNullable(this.address);
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee )) return false;
        return this.id == ((Employee) o).getId();
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
