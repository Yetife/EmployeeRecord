package com.EmployeeRecord.web.controllers;

import com.EmployeeRecord.data.models.Employee;
import com.EmployeeRecord.data.models.dto.EmployeeDto;
import com.EmployeeRecord.service.employee.EmployeeService;
import com.EmployeeRecord.web.exceptions.EmployeeDoesNotExistException;
import com.EmployeeRecord.web.exceptions.EmployeeLogicException;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateEmpoyee(@PathVariable Long id, @RequestBody JsonPatch employeePatch) {
        try{
            Employee updateEmpoyee = employeeService.updateEmployeeRecord(id, employeePatch);
            return ResponseEntity.status(HttpStatus.OK).body(updateEmpoyee);
        }catch (EmployeeLogicException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}
