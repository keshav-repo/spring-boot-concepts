package com.example.employee.service;

import com.example.employee.dto.EmployeeReqDto;
import com.example.employee.dto.EmployeeResDto;
import com.example.employee.exception.DbException;
import com.example.employee.model.Employee;
import com.example.employee.repo.EmployeeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    public void setup() {
        validEmployee = EmployeeReqDto.builder()
                .name("John Doe").role("Software Engineer").department("IT").salary(2000)
                .address("123 Main Street, City, Country").build();
    }

    @Test
    public void saveEmployee_success() {
        when(employeeRepo.save(any(Employee.class))).thenReturn(new Employee());
        employeeService.saveEmployee(validEmployee);
    }

    @Test
    public void saveEmployee_dbError() {
        when(employeeRepo.save(any(Employee.class))).thenThrow(new DbException("", ""));
        assertThrows(DbException.class, () -> employeeService.saveEmployee(validEmployee));
    }

    @Test
    public void fetchEmployee_success() {
        Employee employee = Employee.builder().name("some name").build();
        when(employeeRepo.findById(any(Integer.class))).thenReturn(Optional.of(employee));

        EmployeeResDto employeeResDto = employeeService.fetchEmployee(1);
        assertEquals(employee.getName(), employeeResDto.getName());
    }

    @Test
    public void fetchEmployee_any_error() {
        when(employeeRepo.findById(any(Integer.class))).thenThrow(new RuntimeException());
        assertThrows(DbException.class, () -> employeeService.fetchEmployee(1));
    }

    @Test
    public void deleteEmployee_success() {
        Employee employee = Employee.builder().name("some name").build();
        when(employeeRepo.findById(any(Integer.class))).thenReturn(Optional.of(employee));
        assertDoesNotThrow(() -> employeeService.deleteEmployee(1));
    }

    @Test
    public void fetchAllEmployee_test() {
        when(employeeRepo.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(
                Employee.builder().name("name1").build(), Employee.builder().name("name2").build()
        )));
        List<EmployeeResDto> employeeResDtos =  employeeService.fetchAllEmployee(1, 2);
        assertEquals(2, employeeResDtos.size());
    }
}
