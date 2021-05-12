package ru.profistaff.service;

import ru.profistaff.model.Student;

import java.util.List;

public interface StudentService {
    Student save(Student student);
    Student update(Student student);
    Student delete(Student student);
    Student findById(long id);
    List<Student> findAll();
}
