package com.example.studentmanagement.api;


import com.example.studentmanagement.exceptions.InvalidUniversityClassException;
import com.example.studentmanagement.exceptions.StudentEmptyNameException;
import com.example.studentmanagement.exceptions.StudentNonExistException;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.model.UniversityClass;
import com.example.studentmanagement.service.StudentService;
import com.example.studentmanagement.service.UniversityClassService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {
    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @RequiresPermissions("student:read")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/name")
    // localhost:8080/api/student/name?name=xxx
    public List<Student> getStudentsByName(@RequestParam String name) {
        return studentService.getStudentsByName(name);
    }
    @GetMapping("/matching_str")
    // localhost:8080/api/student/matching_str?matching_str=xxx
    public List<Student> getStudentsByMatchingStr(@RequestParam String matching_str) {
        return studentService.getStudentsByMatchingStr(matching_str);
    }
    @GetMapping("/class")
    // localhost:8080/api/student/matching_str?year=xxx&number=xxx
    public List<Student> getStudentsByClass(@RequestParam int year, int number){
        return studentService.getStudentsByClass(year, number);
    }

    @RequestMapping("/register")
    @PostMapping
    public ResponseEntity<String> registerStudent(@RequestBody Student student){//？？封装知识点
        try{
            Student newStudent = studentService.addStudent(student);
            return ResponseEntity.ok("Registered! " + student.toString());

        }catch (StudentEmptyNameException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping(path = "assignclass/{sid}/{cid}")
    public ResponseEntity<String> assignClass(@PathVariable("sid") Long studentId,
                                              @PathVariable("cid") Long classId){
        try{
            Student student = studentService.assignClass(studentId,classId);
            return ResponseEntity.ok("Class Assigned! " + student.toString());

        }catch (StudentNonExistException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (InvalidUniversityClassException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
