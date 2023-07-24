package com.example.restapi.writer;

import com.example.restapi.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DataWriter implements ItemWriter<Employee> {

    @Override
    public void write(Chunk<? extends Employee> chunk) throws Exception {
        log.info("writing item");
        chunk.getItems().forEach(employee -> log.info(employee.toString()));
    }
}
