package com.example.employee.controller;

import com.example.employee.dto.EmployeeReqDto;
import com.example.employee.dto.EmployeeResDto;
import com.example.employee.exception.EmployeeNotFound;
import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @MockBean
    private EmployeeService employeeService;

    ObjectMapper objectMapper;

    public EmployeeControllerTest() {
        objectMapper = new ObjectMapper();
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void saveEmployee_created() {
        EmployeeReqDto validEmployee = EmployeeReqDto.builder()
                .name("John Doe").role("Software Engineer").department("IT").salary(2000)
                .address("123 Main Street, City, Country").build();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<EmployeeReqDto> entity = new HttpEntity<>(validEmployee, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                createURLWithPort("/api/employee"), entity, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void saveEmployee_inputValidation() throws JsonProcessingException {
        // make name empty for validation error
        EmployeeReqDto validEmployee = EmployeeReqDto.builder()
                .name("").role("Software Engineer").department("IT").salary(2000)
                .address("123 Main Street, City, Country").build();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<EmployeeReqDto> entity = new HttpEntity<>(validEmployee, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                createURLWithPort("/api/employee"), entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        JsonNode jsonNode = objectMapper.readTree(response.getBody());

        assertEquals("ERROR02", jsonNode.get("errorCode").asText());
        assertEquals("enter a valid first name", jsonNode.get("message").asText());
    }

    @Test
    public void fetchEmployee_success() throws JsonProcessingException {
        EmployeeResDto employeeResDto = EmployeeResDto.builder().name("name").build();
        when(employeeService.fetchEmployee(any(Integer.class))).thenReturn(employeeResDto);
        ResponseEntity<String> response = restTemplate.getForEntity(createURLWithPort("/api/employee") + "?empId=20", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        assertEquals("name", jsonNode.get("name").asText());
    }

    @Test
    public void fetchEmployee_not_found() throws JsonProcessingException {
        when(employeeService.fetchEmployee(any(Integer.class))).thenThrow(new EmployeeNotFound("", "ERR01"));
        ResponseEntity<String> response = restTemplate.getForEntity(createURLWithPort("/api/employee") + "?empId=20", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        assertEquals("ERR01", jsonNode.get("errorCode").asText());
    }

    @Test
    public void fetchEmployee_any_other_exception() throws JsonProcessingException {
        when(employeeService.fetchEmployee(any(Integer.class))).thenThrow(new RuntimeException());
        ResponseEntity<String> response = restTemplate.getForEntity(createURLWithPort("/api/employee") + "?empId=20", String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        assertEquals("ERROR03", jsonNode.get("errorCode").asText());
    }
}
