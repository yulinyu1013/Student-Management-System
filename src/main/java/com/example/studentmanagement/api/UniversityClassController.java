package com.example.studentmanagement.api;

import com.example.studentmanagement.exceptions.InvalidUniversityClassException;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.model.UniversityClass;
import com.example.studentmanagement.service.UniversityClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/class")
public class UniversityClassController {
    private UniversityClassService universityClassService;

    @Autowired
    public UniversityClassController(UniversityClassService universityClassService) {
        this.universityClassService = universityClassService;
    }

    @GetMapping
    List<UniversityClass> getAllClasses(){
        return universityClassService.getAllClasses();
    }


    @PostMapping
    @RequestMapping("/add")
    public ResponseEntity<String> addClass(@RequestBody UniversityClass universityClass){
        try {
            UniversityClass newClass = universityClassService.addClass(universityClass);
            return ResponseEntity.ok("Added! " + universityClass.toString());

        }catch (InvalidUniversityClassException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
