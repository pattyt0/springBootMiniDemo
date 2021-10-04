package com.mini.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import student.Student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@SpringBootApplication
@RestController
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping
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
