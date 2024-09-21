package com.example.employee.service;

import com.example.employee.dto.EmployeeReqDto;
import com.example.employee.dto.EmployeeResDto;
import java.util.List;

public interface EmployeeService {
    public void saveEmployee(EmployeeReqDto employeeResDto);
    public EmployeeResDto fetchEmployee(int empId);
    public List<EmployeeResDto>  fetchAllEmployee(int page, int size);

    public EmployeeResDto updateEmployee(EmployeeReqDto employeeResDto);
    public void deleteEmployee(int empId);
}
