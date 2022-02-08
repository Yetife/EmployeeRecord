package com.EmployeeRecord.service.employee;

import com.EmployeeRecord.data.models.Employee;
import com.EmployeeRecord.data.models.dto.EmployeeDto;
import com.EmployeeRecord.data.repository.EmployeeRepository;
import com.EmployeeRecord.web.exceptions.EmployeeDoesNotExistException;
import com.EmployeeRecord.web.exceptions.EmployeeLogicException;
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

    @Override
    public Employee updateEmployeeRecord(Long employeeId, EmployeeDto employeeDto) {
        return null;
    }
}
