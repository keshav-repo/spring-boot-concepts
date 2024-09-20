package com.example.employee.controller;

import com.example.employee.constants.ErrorCode;
import com.example.employee.dto.EmployeeReqDto;
import com.example.employee.dto.ValidationError;
import com.example.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<?> saveEmployee(@RequestBody @Valid EmployeeReqDto employeeReqDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().get(0);
            throw new ValidationError(objectError.getDefaultMessage(), ErrorCode.EMPLOYEE_INPUT_VALIDATION.getCode());
        }

        employeeService.saveEmployee(employeeReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
