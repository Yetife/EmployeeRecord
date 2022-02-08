package com.EmployeeRecord;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class EmployeeRecordApplicationTests {

	@Test
	void contextLoads() {
	}

	@Value("${test.property.name}")
	private String testName;

	@Autowired
	private DataSource dataSource;

	@Test
	void valueExists() {
		assertThat(testName).isEqualTo("EmployeeRecord");
		log.info(testName);
	}

	@Test
	void applicationCanConnectToDatabaseTest() {
		assertThat(dataSource).isNotNull();
		Connection connection;
		try {
			connection = dataSource.getConnection();
			assertThat(connection).isNotNull();
			log.info("Connecting to {}", connection.getSchema());
		}catch (SQLException e){
			e.printStackTrace();
		}
	}

}
