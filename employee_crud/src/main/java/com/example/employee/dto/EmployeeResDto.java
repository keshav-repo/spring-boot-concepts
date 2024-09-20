package com.example.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeResDto {
    private String empId;
    private String name;
    private String role;
    private String department;
    private String address;
    private double salary;
}
