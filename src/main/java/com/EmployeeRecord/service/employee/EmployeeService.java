package com.EmployeeRecord.service.employee;

import com.EmployeeRecord.data.models.Employee;
import com.EmployeeRecord.data.models.dto.EmployeeDto;
import com.EmployeeRecord.web.exceptions.EmployeeDoesNotExistException;
import com.EmployeeRecord.web.exceptions.EmployeeLogicException;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployeeRecord();
    Employee addEmployee(EmployeeDto employeeDto) throws EmployeeLogicException;
    Employee findEmployeeById(Long employeeId) throws EmployeeDoesNotExistException;
    Employee updateEmployeeRecord(Long employeeId, EmployeeDto employeeDto);

}
