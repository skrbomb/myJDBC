package com.example.myjdbc.controller;


import com.example.myjdbc.exception.UserNotFoundException;
import com.example.myjdbc.entities.Student;
import com.example.myjdbc.mapper.StudentRowMapper;
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


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    public StudentController(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/register")
    public String register(@RequestBody Student student){

        String sql= """
                insert into student(name,gender,student_class)
                values (:name,:gender,:studentClass)
                returning id
                """;
        Map<String,Object> params=new HashMap<>();
//        params.put("id",student.getId());
        params.put("name",student.getName());
        params.put("gender",student.getGender());
        params.put("studentClass",student.getStudentClass());

        KeyHolder keyHolder=new GeneratedKeyHolder();


//        jdbcTemplate.update(sql,params);
        jdbcTemplate.update(sql,new MapSqlParameterSource(params),keyHolder);
        int id=keyHolder.getKey().intValue();
        System.out.println("postgresql auto insert "+id);
        return "execute INSERT sql";
    }

    @PostMapping("/batch_register")
    public String batchRegister(@RequestBody List<Student> studentList){

        String sql= """
                insert into student(name,gender,student_class)
                values(:name,:gender,:studentClass)
                """;

        MapSqlParameterSource[] params=new MapSqlParameterSource[studentList.size()];

        for(int i=0;i<studentList.size();i++){
            Student student=studentList.get(i);

            params[i]=new MapSqlParameterSource();
            params[i].addValue("name",student.getName());
            params[i].addValue("gender",student.getGender());
            params[i].addValue("studentClass",student.getStudentClass());
        }

        jdbcTemplate.batchUpdate(sql,params);
        return "execute batch insert sql";
    }

//    @PutMapping("/update/{userId}")
//    public String updateUser(@PathVariable("userId") Integer userId,
//                             @RequestBody Student student){
//        String sql= """
//                update student
//                """
//    }



    @DeleteMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") int userId){
        String sql= """
                delete from student where id=:id
                """;

        Map<String,Object> params=new HashMap<>();
        params.put("id",userId);

        jdbcTemplate.update(sql,params);
        return "execute DELETE sql";
    }

    @GetMapping("/all")
    public List<Student> getAllStudents(){

        String sql= """
                select id,name,gender,student_class from student
                """;

        String countSQL= """
                select count(*) from student
                """;


        Map<String,Object> params=new HashMap<>();

        List<Student> studentList=jdbcTemplate.query(sql,params,new StudentRowMapper());
        Integer count=jdbcTemplate.queryForObject(countSQL,params,Integer.class);
        System.out.println("The count of data is "+count);

        return studentList;
    }

    @GetMapping("/{userId}")
    public Student getStudentById(@PathVariable("userId") Integer userId){

        String sql= """
                select id,name,gender,student_class from student where id=:id
                """;
        Map<String,Object>  params=new HashMap<>();

        params.put("id",userId);

        List<Student> studentList= jdbcTemplate.query(sql,params,new StudentRowMapper());
        if(studentList.size()>0){
            return studentList.get(0);
        }else{
            throw new UserNotFoundException("User with id="+userId+" not found");
        }

    }
}
