package com.example.employee.dto;

import com.example.employee.exception.BaseException;

public class ValidationError extends BaseException {
    public ValidationError(String message, String code) {
        super(message, code);
    }
}
