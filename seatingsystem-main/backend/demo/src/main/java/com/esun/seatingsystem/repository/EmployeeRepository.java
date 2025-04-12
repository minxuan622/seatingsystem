package com.esun.seatingsystem.repository;

import com.esun.seatingsystem.dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<EmployeeDto> getAllEmployees() {
        String sql = """
                SELECT EMP_ID, NAME, EMAIL
                FROM Employee
                ORDER BY EMP_ID
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new EmployeeDto(
                rs.getString("EMP_ID"),
                rs.getString("NAME"),
                rs.getString("EMAIL")
        ));
    }
}