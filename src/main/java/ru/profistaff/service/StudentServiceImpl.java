package ru.profistaff.service;

import ru.profistaff.dao.StudentDao;
import ru.profistaff.dao.StudentDaoImpl;
import ru.profistaff.model.Student;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    private final StudentDao dao = new StudentDaoImpl();


    @Override
    public Student save(Student student) {
        return dao.save(student);
    }

    @Override
    public Student update(Student student) {
        return dao.update(student);
    }

    @Override
    public Student delete(Student student) {
        return dao.delete(student);
    }

    @Override
    public Student findById(long id) {
        return dao.findById(id);
    }

    @Override
    public List<Student> findAll() {
        return dao.findAll();
    }
}
