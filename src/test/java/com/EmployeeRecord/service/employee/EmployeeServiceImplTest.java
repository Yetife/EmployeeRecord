package com.EmployeeRecord.service.employee;

import com.EmployeeRecord.data.models.Employee;
import com.EmployeeRecord.data.models.dto.EmployeeDto;
import com.EmployeeRecord.data.repository.EmployeeRepository;
import com.EmployeeRecord.web.exceptions.EmployeeLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeServiceImplTest {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllEmployeeRecordTest() {
        List<Employee> savedEmployees = employeeService.getAllEmployeeRecord();
        assertThat(employeeRepository.findAll().size()).isEqualTo(5);
    }

    @Test
    void addEmployeeRecordTest() throws EmployeeLogicException {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Samuel");
        employeeDto.setLastName("Adelabi");
        employeeDto.setEmail("samuel@gmail.com");
        employeeDto.setDesignation("Mobile developer");

        employeeService.addEmployee(employeeDto);
        assertThat(employeeRepository.findAll().size()).isEqualTo(6);
    }
}