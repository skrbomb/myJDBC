package com.example.myjdbc.service;


import com.example.myjdbc.dao.StudentDao;
import com.example.myjdbc.entities.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentDao studentDao;

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Student getStudentById(Integer id) {
        return studentDao.getStudentById(id);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }

    @Override
    public Integer deleteStudentById(Integer id) {
        getStudentById(id);
        return studentDao.deleteStudentById(id);
    }

    @Override
    public Integer registerStudent(Student student) {
        return studentDao.registerStudent(student);
    }

    @Override
    public void registerBatchStudent(List<Student> studentList) {
        studentDao.registerBatchStudent(studentList);
    }

    @Override
    public Integer updateStudent(Integer id, Student student) {
        return studentDao.updateStudent(id,student);
    }
}
