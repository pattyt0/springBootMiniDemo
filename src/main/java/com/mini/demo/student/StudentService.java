package com.mini.demo.student;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private Logger logger = LogManager.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return this.studentRepository.findAll();
    }

    public Student saveStudent(Student student) throws HttpClientErrorException {
        if (student != null && student.dob.isAfter(LocalDateTime.now().toLocalDate())) {
            logger.error("{} doesn't have the age required to be registered: {}", Student.class.getSimpleName(), student);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Students must be at least 6 years old");
        }
        return this.studentRepository.save(student);
    }

    public Optional<Student> deleteStudent(long id) {
        Optional<Student> studentToDelete = this.studentRepository.findById(id);
        if (studentToDelete.isPresent()) {
            this.studentRepository.deleteById(id);
            logger.debug("{} was deleted: {}", Student.class.getSimpleName(), studentToDelete.get());
            return studentToDelete;
        }
        logger.debug("{} with ID {} not found. Deletion skipped", Student.class.getSimpleName(), id);
        return Optional.empty();
    }

    @Transactional
    public Optional<Student> updateStudent(String id, Student student) {
        Optional<Student> existentStudent = this.studentRepository.findById(Long.parseLong(id));
        if (existentStudent.isPresent()) {
            logger.info("{} with ID {} will be updated to: {}", Student.class.getSimpleName(), id, existentStudent.get());
            Student studentUpdate = existentStudent.get();
            studentUpdate.setName(student.getName());
            studentUpdate.setEmail(student.getEmail());
            studentUpdate.setDob(student.getDob());
            return Optional.of(studentUpdate);
        }
        return Optional.empty();
    }

    public Optional<Student> getStudentById(String id) {
        Optional<Student> studentFound = this.studentRepository.findById(Long.valueOf(id));
        if (studentFound.isPresent()) {
            logger.info("Student {} found: ", studentFound.get());
        } else {
            logger.error("Student with ID {} not found: ", id);
        }
        return studentFound;
    }
}
