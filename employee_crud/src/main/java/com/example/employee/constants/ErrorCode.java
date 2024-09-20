package com.example.employee.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    SAVE_EMPLOYEE_ERR("ERROR01", "Db error saving employee information");

    private final String code;
    private final String message;
}
