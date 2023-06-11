package com.example.demo;

public class ErrorMessage {
    private String errorCode;
    private String errorDescription;

    public ErrorMessage(String errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
