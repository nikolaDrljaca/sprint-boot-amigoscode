package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getStudents() {
        return repository.findAll()
            .stream()
            .sorted((Comparator.comparing(Student::getId)))
            .collect(Collectors.toList());
    }

    /*
    Check if any students have the same email as the one coming in
    If yes - throw exception
    buSsiNess LoGIc brrrr
     */
    public void addNewStudent(Student student) {
        doesEmailExist(student.getEmail());
        repository.save(student);
    }

    public void deleteStudent(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalStateException("Student with ID:" + id + " does not exist.");
        }
        repository.deleteById(id);
    }

    @Transactional
    public void updateStudent(Long id, String name, String email) {
        if (!repository.existsById(id)) {
            throw new IllegalStateException("Cannot update, student with ID:" + id + " does not exist.");
        }

        repository.findById(id)
            .ifPresent(student -> {
                if (validStudentInput(name, student.getName()))
                    student.setName(name);

                if (validStudentInput(email, student.getEmail())) {
                    doesEmailExist(email);
                    student.setEmail(email);
                }
            });
    }

    private boolean validStudentInput(String newValue, String currentValue) {
        return newValue != null && newValue.length() > 0 && !Objects.equals(newValue, currentValue);
    }

    private void doesEmailExist(String email) {
        if(repository.findStudentByEmail(email).isPresent())
            throw new IllegalStateException("Email already taken.");
    }
}
