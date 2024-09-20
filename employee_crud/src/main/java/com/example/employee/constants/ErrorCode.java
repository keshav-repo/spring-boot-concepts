package com.example.employee.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SAVE_EMPLOYEE_ERR("ERROR01", "Db error saving employee information"),
    EMPLOYEE_INPUT_VALIDATION("ERROR02", "Bad request, check input"),

    TEMP_ERR("ERROR03", "Some temporary error"),
    EMPLOYEE_NOTFOUND("ERROR04", "Employee not found"),
    DELETE_EMPLOYEE_ERR("ERROR05", "Error deleting employee");

    private final String code;
    private final String message;
}
