package com.mini.demo.student;

import org.springframework.stereotype.Service;
import student.Student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class StudentService {
    public StudentService() {
    }

    public List<Student> getStudents() {
        return List.of(new Student(
                "1",
                "Patricia",
                "patty@mail.com",
                LocalDate.of(1990, Month.APRIL, 19),
                "25"
        ));
    }
}
