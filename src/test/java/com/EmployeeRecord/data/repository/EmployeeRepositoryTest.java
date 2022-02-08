package com.EmployeeRecord.data.repository;

import com.EmployeeRecord.data.models.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"/db/insert.sql"})
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Add employee record to the database")
    void addEmployeeRecordTest() {
        Employee employee = new Employee();
        employee.setFirstName("Yetunde");
        employee.setLastName("Olasehinde");
        employee.setEmail("olasehindey3@gmail.com");
        employee.setPhoneNumber("08096393031");
        employee.setDesignation("Receptionist");

        assertThat(employee.getId()).isNull();
        employeeRepository.save(employee);
        log.info("Employee added : {}", employee);
        assertThat(employee.getId()).isNotNull();
        assertThat(employee.getEmail()).isNotNull();
        assertThat(employee.getPhoneNumber()).isEqualTo("08096393031");
        assertThat(employee.getFirstName()).isEqualTo("Yetunde");
        assertThat(employee.getLastName()).isEqualTo("Olasehinde");
    }

    @Test
    @DisplayName("Get all employee record from database test")
    void getAllEmployeeRecordTest() {
       List<Employee> employeeRecordList = employeeRepository.findAll();
        assertThat(employeeRecordList).isNotNull();
        assertThat(employeeRecordList.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("Update employee record test")
    void updateemployeerecordTest() {
        Employee savedEmployee = employeeRepository.findById(4L).orElse(null);
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isEqualTo(4L);
        assertThat(savedEmployee.getLastName()).isEqualTo("Olasehinde");
        assertThat(savedEmployee.getEmail()).isEqualTo("olasehindey3@gmail.com");
        savedEmployee.setFirstName("Boluwatife");

        employeeRepository.save(savedEmployee);
        assertThat(savedEmployee.getFirstName()).isEqualTo("Boluwatife");
    }

    @Test
    @DisplayName("Get employee by id test")
    void findAnEmployeeByIdTest() {
        Employee employee = employeeRepository.findById(11L).orElse(null);
        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(11L);
        assertThat(employee.getFirstName()).isEqualTo("Yetunde");
        assertThat(employee.getLastName()).isEqualTo("Olasehinde");
        assertThat(employee.getEmail()).isEqualTo("olasehinde@gmail.com");
    }

    @Test
    @DisplayName("Delete employee records test")
    void deleteAllEmployeeRecordTest() {
        employeeRepository.deleteAll();
        assertThat(employeeRepository.count()).isEqualTo(0);
    }
}