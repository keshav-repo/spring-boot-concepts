package com.demo.service;

import com.demo.entity.Employee;
import com.demo.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Create
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Read all
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Read by ID
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Employee not found with id: " + id
                        )
                );
    }

    // Update
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = getEmployeeById(id);

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setSalary(updatedEmployee.getSalary());

        return employeeRepository.save(existingEmployee);
    }

    // Delete
    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }
}