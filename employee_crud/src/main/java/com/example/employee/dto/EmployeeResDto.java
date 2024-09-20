package com.example.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeResDto {
    private int empId;
    private String name;
    private String role;
    private String department;
    private String address;
    private double salary;
}
