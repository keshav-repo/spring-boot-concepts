package com.example.employee.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeReqDto {

    @NotNull(message="enter a valid first name")
    @NotBlank(message="enter a valid first name")
    private String name;

    private String role;

    @NotNull(message="enter a valid department name")
    @NotBlank(message="enter a valid department name")
    private String department;

    private String address;

    @Min(value = 1, message = "Salary must be greater than 0")
    private double salary;
}
