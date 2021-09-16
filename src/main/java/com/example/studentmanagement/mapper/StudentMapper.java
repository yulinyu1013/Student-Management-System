package com.example.studentmanagement.mapper;

import com.example.studentmanagement.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {//不需要实现；mybatis会帮忙实现好

    //SELECT * FROM student WHERE name LIKE %T%
    @Select("SELECT * FROM student WHERE name LIKE #{str}")
    List<Student> getStudentByMatchingStr(@Param("str") String matchingStr);

    //SELECT * FROM student WHERE univ_id IN
    //(SELECT id FROM university_class WHERE year=2021 AND number=4)
    @Select("SELECT * FROM student WHERE university_class_id IN" +
            "(SELECT id FROM university_class WHERE year=#{year} AND number=#{number})")
    List<Student> getStudentsByClass(@Param("year") int year,
                                     @Param("number") int number);

}
