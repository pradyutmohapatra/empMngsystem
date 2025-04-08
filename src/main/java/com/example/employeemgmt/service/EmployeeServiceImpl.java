package com.example.employeemgmt.service;

import com.example.employeemgmt.model.Employee;
import com.example.employeemgmt.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        return employeeRepository.findById(id).map(emp -> {
            emp.setName(updatedEmployee.getName());
            emp.setEmail(updatedEmployee.getEmail());
            emp.setDesignation(updatedEmployee.getDesignation());
            emp.setSalary(updatedEmployee.getSalary());
            emp.setImage(updatedEmployee.getImage());
            return employeeRepository.save(emp);
        }).orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }

//    @Override
//    public List<Employee> getAllEmployees() {
//        return employeeRepository.findAll();
//    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    @Override
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }
}
