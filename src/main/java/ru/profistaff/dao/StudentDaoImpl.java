package ru.profistaff.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.profistaff.model.Student;

import java.util.Comparator;
import java.util.List;


public class StudentDaoImpl implements StudentDao {
    @Override
    public Student save(Student student) {
        Session session = SessionFactoryUtil.getInstance().openSession();
        Transaction tx = session.beginTransaction();

        session.save(student);
        tx.commit();

        session.close();

        return student;
    }

    @Override
    public Student update(Student student) {
        Session session = SessionFactoryUtil.getInstance().openSession();
        Transaction tx = session.beginTransaction();

        session.update(student);
        tx.commit();

        session.close();

        return student;
    }

    @Override
    public Student delete(Student student) {
        Session session = SessionFactoryUtil.getInstance().openSession();
        Transaction tx = session.beginTransaction();

        session.delete(student);
        tx.commit();

        session.close();

        return student;
    }


    @Override
    public Student findById(long id) {
        Session session = SessionFactoryUtil.getInstance().getCurrentSession();
        Transaction tx = session.beginTransaction();
        Student student = session.find(Student.class, id);

        tx.commit();
        session.close();

        return student;
    }

    @Override
    public List<Student> findAll() {
        List<Student> students;
        Session session = SessionFactoryUtil.getInstance().getCurrentSession();
        Transaction tx = session.beginTransaction();
        students = (List<Student>) session.createQuery("From Student").list();

        tx.commit();
        session.close();

        students.stream().sorted(Comparator.comparingLong(Student::getId));

        return students;
    }

    private static class SessionFactoryUtil {
        private static SessionFactory sessionFactory;

        private SessionFactoryUtil() {}

        public static SessionFactory getInstance() {
            if (sessionFactory == null) {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Student.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            }

            return sessionFactory;
        }
    }
}
