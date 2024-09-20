package com.example.employee.service;

import com.example.employee.dto.EmployeeReqDto;
import com.example.employee.exception.DbException;
import com.example.employee.model.Employee;
import com.example.employee.repo.EmployeeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployeeServiceTest {
    @Autowired
    private EmployeeService employeeService;
    @MockBean
    private EmployeeRepo employeeRepo;

    private EmployeeReqDto validEmployee;

    @BeforeEach
    public void setup(){
        validEmployee = EmployeeReqDto.builder()
                .name("John Doe").role("Software Engineer").department("IT").salary(2000)
                .address("123 Main Street, City, Country").build();
    }

    @Test
    public void saveEmployee_success(){
        when(employeeRepo.save(any(Employee.class))).thenReturn(new Employee());
        employeeService.saveEmployee(validEmployee);
    }
    @Test
    public void saveEmployee_dbError(){
        when(employeeRepo.save(any(Employee.class))).thenThrow(new DbException("", ""));
        assertThrows(DbException.class,()->employeeService.saveEmployee(validEmployee) );
    }


}
