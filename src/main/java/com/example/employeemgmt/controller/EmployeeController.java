package com.example.employeemgmt.controller;

import com.example.employeemgmt.model.Employee;
import com.example.employeemgmt.repository.EmployeeRepository;
import com.example.employeemgmt.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;



    // Show form to add employee
    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add-employee";
    }

    // Save employee


    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute Employee employee,
                               @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            employee.setImage(imageFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }



    // Show form to update employee
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "update-employee";
    }

    // Update employee
    @PostMapping("/updateEmployee/{id}")
    public String updateEmployee(@PathVariable("id") long id,
                                 @ModelAttribute("employee") Employee employee,
                                 @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        Employee existing = employeeService.getEmployeeById(id);
        employee.setId(id);

        if (!imageFile.isEmpty()) {
            employee.setImage(imageFile.getBytes());
        } else {
            employee.setImage(existing.getImage()); // keep existing image
        }

        employeeService.updateEmployee(id, employee);
        return "redirect:/";
    }

    // Delete employee
    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id) {
        employeeService.deleteEmployeeById(id);
        return "redirect:/";
    }



    @GetMapping("/")
    public String getAllEmployees(Model model) {
        List<Employee> employees = employeeRepository.findAll();

        List<Map<String, Object>> employeeList = employees.stream().map(emp -> {
            Map<String, Object> data = new HashMap<>();
            data.put("id", emp.getId());
            data.put("name", emp.getName());
            data.put("email", emp.getEmail());
            data.put("designation", emp.getDesignation());
            data.put("salary", emp.getSalary());
            if (emp.getImage() != null) {
                String base64Image = Base64.getEncoder().encodeToString(emp.getImage());
                data.put("image", base64Image);
            } else {
                data.put("image", null);
            }
            return data;
        }).collect(Collectors.toList());

        model.addAttribute("listEmployees", employeeList); // 🔥 Match the view
        return "employees";
    }


}
