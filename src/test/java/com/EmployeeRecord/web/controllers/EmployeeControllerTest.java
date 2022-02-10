package com.EmployeeRecord.web.controllers;

import com.EmployeeRecord.data.models.Employee;
import com.EmployeeRecord.data.repository.EmployeeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts={"/db/insert.sql"})
class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EmployeeRepository employeeRepository;

    ObjectMapper ObjectMapper;

    @BeforeEach
    void setUp() {
        ObjectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Get employee api test")
    void getEmployeeTest() throws Exception {
        mockMvc.perform(get("/api/employee")
                .contentType("application/json")).
                andExpect(status().is(200)).andDo(print());
    }

    @Test
    @DisplayName("Add Employee api test")
    void addEmployeeTest() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("Abigail");
        employee.setLastName("Olasehinde");
        employee.setEmail("abigail@gmail.com");
        employee.setDesignation("web developer");

        String requestBody = ObjectMapper.writeValueAsString(employee);

        mockMvc.perform(post("/api/employee").contentType("application/json")
                .content(requestBody)).andExpect(status().is(200)).andDo(print());
    }

    @Test
    @DisplayName("Update employee api test")
    void updateEmployeerecordTest() throws Exception {
        Employee employee = employeeRepository.findById(11L).orElse(null);
        assertThat(employee).isNotNull();

        mockMvc.perform(patch("/api/employee/11")
                        .contentType("application/json-patch+json")
                        .content(Files.readAllBytes(Path.of("payload.json"))))
                .andExpect(status().is(200))
                .andDo(print());

        employee = employeeRepository.findById(11L).orElse(null);
        assertThat(employee).isNotNull();
        assertThat(employee.getEmail()).isEqualTo("olaBolu@gmail.com");
    }
}