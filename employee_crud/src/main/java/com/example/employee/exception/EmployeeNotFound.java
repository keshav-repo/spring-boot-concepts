package com.example.employee.exception;

public class EmployeeNotFound extends BaseException{
    public EmployeeNotFound(String message, String code) {
        super(message, code);
    }
}
