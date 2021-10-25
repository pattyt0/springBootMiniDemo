package com.mini.demo.student;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class StudentController {
    private Logger logger = LogManager.getLogger(StudentController.class);
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getStudents() {
        logger.info("Retrieve all students");
        return new ResponseEntity(studentService.getStudents(), HttpStatus.OK);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable String id) {
        logger.info("Retrieve a student by id: {}", id);
        Optional<Student> studentFound = studentService.getStudentById(id);
        return ResponseEntity.of(studentFound);
    }

    @PostMapping("/students")
    public ResponseEntity<Student> postStudent(@RequestBody Student student) throws HttpClientErrorException {
        logger.info("Will create {}: {}", Student.class.getSimpleName(), student);
        try {
            return new ResponseEntity(studentService.saveStudent(new Student(student.name, student.email, student.dob)),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("{} could not create {} due to: {}", e.getClass().getSimpleName(), Student.class.getSimpleName(), e.getMessage());
            String message = String.format("{} {}", e.getClass().getSimpleName(), e.getMessage());
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Student> postStudent(@PathVariable String id) {
        logger.info("{} with ID {} will be deleted",Student.class.getSimpleName(), id);
        Optional<Student> student = studentService.deleteStudent(Long.parseLong(id));
        return ResponseEntity.of(student);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable String id, @RequestBody Student student) {
        logger.info("{} is going to be updated to: {}", Student.class.getSimpleName(), student);
        Optional<Student> response = studentService.updateStudent(id, student);
        return ResponseEntity.of(response);
    }
}
