package com.EmployeeRecord.web.controllers;

import com.EmployeeRecord.data.models.Employee;
import com.EmployeeRecord.data.models.dto.EmployeeDto;
import com.EmployeeRecord.service.employee.EmployeeService;
import com.EmployeeRecord.web.exceptions.EmployeeDoesNotExistException;
import com.EmployeeRecord.web.exceptions.EmployeeLogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping()
    public ResponseEntity<?> getAllEmployeeRecord() {
        List<Employee> employeeRecords = employeeService.getAllEmployeeRecord();
        return ResponseEntity.ok().body(employeeRecords);
    }

    @PostMapping()
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDto employeeDto) {
        try{
            Employee savedEmployee = employeeService.addEmployee(employeeDto);
            return ResponseEntity.ok().body(savedEmployee);
        }catch (EmployeeLogicException | IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
