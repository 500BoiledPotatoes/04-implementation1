package com.example.demo.system;

import com.example.demo.system.Error.ErrorType;

public class ErrorDetails {
    ErrorType errorType;
    String detail;
    ErrorDetails(ErrorType errorType,String detail){
        this.detail=detail;
        this.errorType=errorType;

    }
}
