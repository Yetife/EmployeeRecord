package com.EmployeeRecord.web.exceptions;

public class EmployeeDoesNotExistException extends Throwable {
    public EmployeeDoesNotExistException(String message) {
        super(message);
    }
}
