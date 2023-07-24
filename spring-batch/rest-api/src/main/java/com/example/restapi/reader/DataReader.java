package com.example.restapi.reader;

import com.example.restapi.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DataReader implements ItemReader<Employee> {

    private List<Employee> employeeList;
    private int idx;

    public DataReader() {
        employeeList = new ArrayList<>(List.of(
                Employee.builder().empId(1).name("John Doe").department("Sales").build(),
                Employee.builder().empId(1).name("Nick Doe").department("Sales").build(),
                Employee.builder().empId(1).name("Harry Doe").department("Marketing").build(),
                Employee.builder().empId(1).name("Alisa Doe").department("Admin").build()
        ));
        idx = 0;
    }

    @Override
    public Employee read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return idx < employeeList.size() ? employeeList.get(idx++) : null;
    }
}
