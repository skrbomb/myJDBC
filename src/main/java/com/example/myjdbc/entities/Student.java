package com.example.myjdbc.entities;

public class Student {

    private int id;
    private String name;
    private String gender;
    private int studentClass;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(int studentClass) {
        this.studentClass = studentClass;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Student(int id, String name, int studentClass, String gender) {
        this.id = id;
        this.name = name;
        this.studentClass = studentClass;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", studentClass=" + studentClass +
                '}';
    }
}
