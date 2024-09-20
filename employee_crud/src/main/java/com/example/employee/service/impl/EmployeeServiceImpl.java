package com.example.employee.service.impl;

import com.example.employee.constants.ErrorCode;
import com.example.employee.dto.EmployeeReqDto;
import com.example.employee.dto.EmployeeResDto;
import com.example.employee.exception.DbException;
import com.example.employee.model.Employee;
import com.example.employee.repo.EmployeeRepo;
import com.example.employee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public void saveEmployee(EmployeeReqDto employeeResDto) {
        Employee employee = Employee.builder()
                .name(employeeResDto.getName())
                .role(employeeResDto.getRole())
                .department(employeeResDto.getDepartment())
                .address(employeeResDto.getAddress())
                .salary(employeeResDto.getSalary())
                .build();
        try {
            employeeRepo.save(employee);
        } catch (Exception exception) {
            log.error("error saving employee information");
            throw new DbException(ErrorCode.SAVE_EMPLOYEE_ERR.getMessage(), ErrorCode.SAVE_EMPLOYEE_ERR.getCode() );
        }
    }

    @Override
    public EmployeeResDto fetchEmployee(int empId) {
        return null;
    }

    @Override
    public List<EmployeeResDto> fetchAllEmployee() {
        return null;
    }

    @Override
    public void deleteEmployee(int empId) {

    }
}
