package com.example.myjdbc.dao;

import com.example.myjdbc.entities.Student;
import com.example.myjdbc.exception.UserNotFoundException;
import com.example.myjdbc.mapper.StudentRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StduentDaoImpl implements StudentDao{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StduentDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Student getStudentById(Integer id) {

        String sql= """
                select id,name,gender,student_class from student where id=:id
                """;
        Map<String,Object> params=new HashMap<>();

        params.put("id",id);

        List<Student> studentList= jdbcTemplate.query(sql,params,new StudentRowMapper());
        if(studentList.size()>0){
            return studentList.get(0);
        }else{
            throw new UserNotFoundException("User with id="+id+" not found");
        }
    }

    @Override
    public List<Student> getAllStudents() {
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

    @Override
    public Integer deleteStudentById(Integer id) {

        String sql= """
                delete from student where id=:id
                """;

        Map<String,Object> params=new HashMap<>();
        params.put("id",id);

        int updatedRow = jdbcTemplate.update(sql, params);

        return updatedRow;
    }


    @Override
    public Integer registerStudent(Student student) {

        String sql= """
                insert into student(name,gender,student_class)
                values (:name,:gender,:studentClass)
                returning id
                """;
        Map<String,Object> params=new HashMap<>();
        params.put("name",student.getName());
        params.put("gender",student.getGender());
        params.put("studentClass",student.getStudentClass());

        KeyHolder keyHolder=new GeneratedKeyHolder();

//        jdbcTemplate.update(sql,params);
        jdbcTemplate.update(sql,new MapSqlParameterSource(params),keyHolder);

        int id=keyHolder.getKey().intValue();
        System.out.println("postgresql auto insert "+id);

        return id;
    }

    @Override
    public void registerBatchStudent(List<Student> studentList) {
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
    }

    @Override
    public Integer updateStudent(Integer id, Student student) {
        String sql= """
                update student set name=:name,gender=:gender,student_class=:studentClass
                where id=:id
                """;

        Map<String,Object> params=new HashMap<>();
        params.put("id",id);
        params.put("name",student.getName());
        params.put("gender",student.getGender());
        params.put("studentClass",student.getStudentClass());

        int updated = jdbcTemplate.update(sql, params);
        return updated;
    }
}
