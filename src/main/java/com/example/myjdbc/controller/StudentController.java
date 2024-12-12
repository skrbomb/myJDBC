package com.example.myjdbc.controller;


import com.example.myjdbc.exception.UserNotFoundException;
import com.example.myjdbc.entities.Student;
import com.example.myjdbc.mapper.StudentRowMapper;
import com.example.myjdbc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {



    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final StudentService studentService;


    public StudentController(NamedParameterJdbcTemplate jdbcTemplate, StudentService studentService) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public String register(@RequestBody Student student){

        Integer newStudentId = studentService.registerStudent(student);
        return "Register Success! New Student Id: " + newStudentId;
    }

    @PostMapping("/batch_register")
    public String batchRegister(@RequestBody List<Student> studentList){

        studentService.registerBatchStudent(studentList);
        return "Batch Register Success!";
    }


    @DeleteMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") int userId){

        Integer deleteCount=studentService.deleteStudentById(userId);
        return "delete "+deleteCount+" row from student";
    }

    @GetMapping("/all")
    public List<Student> getAllStudents(){

        return studentService.getAllStudents();
    }

    @GetMapping("/{userId}")
    public Student getStudentById(@PathVariable("userId") Integer userId){

        return  studentService.getStudentById(userId);

    }

    @PutMapping("/update/{userId}")
    public String updateStudent(@PathVariable("userId") Integer userId,
                                @RequestBody Student student){

        Integer updatedRowCount= studentService.updateStudent(userId,student);
        return "update "+updatedRowCount+" row from student";

    }
}
