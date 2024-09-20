package com.example.employee.controller;

import com.example.employee.dto.EmployeeReqDto;
import com.example.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<?> saveEmployee(@Valid @RequestBody EmployeeReqDto employeeReqDto) {
        employeeService.saveEmployee(employeeReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
