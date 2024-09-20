package com.example.employee.exception;

import com.example.employee.constants.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String errorCode;
    private String message;
    public ErrorResponse(ErrorCode errorCode){
        this.errorCode = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
