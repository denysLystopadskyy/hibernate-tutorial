package com.StudentDemo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Student;

public class QueryUpdateStudentDemo {

    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {

            session.beginTransaction();

            Student student = session.get(Student.class, 3);
            System.out.println(student);
            student.setFirstName("Updated FN");

            session.getTransaction().commit();


            session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            student = session.get(Student.class, 1);
            System.out.println(student);

            session.getTransaction().commit();

            System.out.println("Done!");

        } finally {
            sessionFactory.close();
        }
    }

    private static void printStudents(List<Student> students) {
        students.forEach(System.out::println);
    }
}
