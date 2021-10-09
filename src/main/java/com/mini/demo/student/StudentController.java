package com.mini.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping("/students")
    public void postStudent(@RequestBody Student student) {
        studentService.saveStudent(new Student(student.name, student.email, student.dob));
    }

    @DeleteMapping("/students/{id}")
    public Student postStudent(@PathVariable String id) {
        Optional<Student> student = studentService.deleteStudent(Long.parseLong(id));
        return student.orElseGet(Student::new);
    }

    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable String id, @RequestBody Student student) {
        Optional<Student> response = studentService.updateStudent(id, student);
        return response.orElseGet(Student::new);
    }

}
