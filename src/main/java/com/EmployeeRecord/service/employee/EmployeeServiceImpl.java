package com.EmployeeRecord.service.employee;

import com.EmployeeRecord.data.models.Employee;
import com.EmployeeRecord.data.models.dto.EmployeeDto;
import com.EmployeeRecord.data.repository.EmployeeRepository;
import com.EmployeeRecord.web.exceptions.EmployeeDoesNotExistException;
import com.EmployeeRecord.web.exceptions.EmployeeLogicException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployeeRecord() {

        return employeeRepository.findAll();
    }

    @Override
    public Employee addEmployee(EmployeeDto employeeDto) throws EmployeeLogicException {
        if (employeeDto == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }
        Optional<Employee> query = employeeRepository.findByEmail(employeeDto.getEmail());
        if (query.isPresent()){
            throw new EmployeeLogicException("Employee with email" + " already exists");
        }

        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setDesignation(employeeDto.getDesignation());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        return employeeRepository.save(employee);
    }

    @Override
    public Employee findEmployeeById(Long employeeId) throws EmployeeDoesNotExistException {
        if(employeeId == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        Optional<Employee> queryResult = employeeRepository.findById(employeeId);
        if(queryResult.isPresent()){
            return queryResult.get();
        }
        throw new EmployeeDoesNotExistException("Employee with Id " + employeeId + "does not exist");
    }

    private Employee saveEmployee(Employee employee) throws EmployeeLogicException {
        if (employee == null){
            throw new EmployeeLogicException("employee cannot be null");
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployeeRecord(Long employeeId, JsonPatch employeePatch) throws EmployeeLogicException {
        Optional<Employee> employeeQuery = employeeRepository.findById(employeeId);
        if(employeeQuery.isEmpty()) {
            throw new EmployeeLogicException("Employee with Id "+ employeeId + "does not exist");
        }
        Employee targetEmployee = employeeQuery.get();

        try{
            targetEmployee = applyPatchToEmployeeRecord(employeePatch, targetEmployee);
            return saveEmployee(targetEmployee);
        }catch(JsonPatchException | JsonProcessingException | EmployeeLogicException e){
            throw new EmployeeLogicException("Update failed");
        }
    }

    private Employee applyPatchToEmployeeRecord(JsonPatch employeePatch, Employee targetEmployee) throws JsonProcessingException, JsonPatchException {
        ObjectMapper ObjectMapper = new ObjectMapper();
        JsonNode patched = employeePatch.apply(ObjectMapper.convertValue(targetEmployee, JsonNode.class));
        return ObjectMapper.treeToValue(patched, Employee.class);

    }
}
