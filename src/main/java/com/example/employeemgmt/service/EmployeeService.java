package com.example.employeemgmt.service;

import com.example.employeemgmt.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    Employee updateEmployee(Long id, Employee employee);
//    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    void deleteEmployeeById(Long id);
}
