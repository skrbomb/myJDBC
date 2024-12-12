package com.example.myjdbc.mapper;

import com.example.myjdbc.entities.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        return new Student(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("student_class"),
                resultSet.getString("gender")
        );
    }
}
