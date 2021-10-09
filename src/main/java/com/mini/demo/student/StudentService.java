package com.mini.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return this.studentRepository.findAll();
    }

    public void saveStudent(Student student) {
        this.studentRepository.save(student);
    }

    public Optional<Student> deleteStudent(long id) {
        Optional<Student> studentToDelete = this.studentRepository.findById(id);
        System.out.println("Student found: " + studentToDelete);
        if (studentToDelete.isPresent()) {
            this.studentRepository.deleteById(id);
            return studentToDelete;
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<Student> updateStudent(String id, Student student) {
        Optional<Student> existentStudent = this.studentRepository.findById(Long.parseLong(id));
        if (existentStudent.isPresent()) {
            System.out.println("updateStudent Student found:" + existentStudent.get());
            Student studentUpdate = existentStudent.get();
            if (!studentUpdate.getEmail().equals(student.getEmail())) {
                studentUpdate.setEmail(student.getEmail());
            }

            if (!studentUpdate.getDob().equals(student.getDob())) {
                studentUpdate.setDob(student.getDob());
            }
            return Optional.of(studentUpdate);
        }
        return Optional.empty();
    }
}
