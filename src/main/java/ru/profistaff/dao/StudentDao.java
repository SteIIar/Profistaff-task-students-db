package ru.profistaff.dao;

import ru.profistaff.model.Student;

import java.util.List;

public interface StudentDao {

    Student save(Student student);
    Student update(Student student);
    Student delete(Student student);
    Student findById(long id);
    List<Student> findAll();
}
