package com.example.employee.controller;

import com.example.employee.dto.EmployeeReqDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void saveEmployee_created(){
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

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());

        assertEquals("ERROR02", jsonNode.get("errorCode").asText());
        assertEquals("enter a valid first name", jsonNode.get("message").asText());
    }
}
