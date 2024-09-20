package com.example.employee.service.impl;

import com.example.employee.constants.ErrorCode;
import com.example.employee.dto.EmployeeReqDto;
import com.example.employee.dto.EmployeeResDto;
import com.example.employee.exception.DbException;
import com.example.employee.exception.EmployeeNotFound;
import com.example.employee.model.Employee;
import com.example.employee.repo.EmployeeRepo;
import com.example.employee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            throw new DbException(ErrorCode.SAVE_EMPLOYEE_ERR.getMessage(), ErrorCode.SAVE_EMPLOYEE_ERR.getCode());
        }
    }

    @Override
    public EmployeeResDto fetchEmployee(int empId) {
        try {
            Optional<Employee> opTemployee = employeeRepo.findById(empId);
            if (opTemployee.isPresent()) {
                Employee employee = opTemployee.get();
                return EmployeeResDto.builder()
                        .empId(employee.getEmpId())
                        .name(employee.getName())
                        .role(employee.getRole())
                        .address(employee.getAddress())
                        .department(employee.getDepartment())
                        .salary(employee.getSalary())
                        .build();
            } else {
                throw new EmployeeNotFound(ErrorCode.EMPLOYEE_NOTFOUND.getMessage(), ErrorCode.EMPLOYEE_NOTFOUND.getCode());
            }
        } catch (Exception e) {
            if (e instanceof EmployeeNotFound)
                throw e;
            log.error("error fetching employee information with empId {}", empId);
            e.printStackTrace();
            throw new DbException(ErrorCode.SAVE_EMPLOYEE_ERR.getMessage(), ErrorCode.SAVE_EMPLOYEE_ERR.getCode());
        }
    }

    @Override
    public EmployeeResDto updateEmployee(EmployeeReqDto employeeResDto) {
        Employee employee = Employee.builder()
                .empId(employeeResDto.getEmpId())
                .name(employeeResDto.getName())
                .role(employeeResDto.getRole())
                .department(employeeResDto.getDepartment())
                .address(employeeResDto.getAddress())
                .salary(employeeResDto.getSalary())
                .build();
        try {
            Employee employeeUpdated = employeeRepo.save(employee);
            return EmployeeResDto.builder()
                    .empId(employeeUpdated.getEmpId())
                    .name(employeeUpdated.getName())
                    .role(employeeUpdated.getRole())
                    .department(employeeUpdated.getDepartment())
                    .address(employeeUpdated.getAddress())
                    .salary(employee.getSalary())
                    .build();
        } catch (Exception exception) {
            log.error("error saving employee information");
            exception.printStackTrace();
            throw new DbException(ErrorCode.SAVE_EMPLOYEE_ERR.getMessage(), ErrorCode.SAVE_EMPLOYEE_ERR.getCode());
        }
    }

    @Override
    public void deleteEmployee(int empId) {
        try {
            Optional<Employee> optionalEmployee = employeeRepo.findById(empId);
            if(!optionalEmployee.isPresent()){
                throw new EmployeeNotFound(ErrorCode.EMPLOYEE_NOTFOUND.getMessage(), ErrorCode.EMPLOYEE_NOTFOUND.getCode());
            }else{
                employeeRepo.deleteById(empId);
            }
        } catch (Exception e) {
            if (e instanceof EmployeeNotFound)
                throw e;
            log.error("error deleting employee");
            e.printStackTrace();
            throw new DbException(ErrorCode.DELETE_EMPLOYEE_ERR.getMessage(), ErrorCode.DELETE_EMPLOYEE_ERR.getCode());
        }
    }
}
