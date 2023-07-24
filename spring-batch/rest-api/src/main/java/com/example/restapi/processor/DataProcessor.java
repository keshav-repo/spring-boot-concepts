package com.example.restapi.processor;

import com.example.restapi.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
@Component
@Slf4j
public class DataProcessor implements ItemProcessor<Employee, Employee> {
    @Override
    public Employee process(Employee employees) throws Exception {
        return employees;
    }
}
