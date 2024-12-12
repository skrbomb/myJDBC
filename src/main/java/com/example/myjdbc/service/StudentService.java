package com.example.myjdbc.service;

import com.example.myjdbc.entities.Student;

import java.util.List;

public interface StudentService {

    Student getStudentById(Integer id);

    List<Student> getAllStudents();

    Integer deleteStudentById(Integer id);

    Integer registerStudent(Student student);

    void registerBatchStudent(List<Student> studentList);

    Integer updateStudent(Integer id,Student student);
}
