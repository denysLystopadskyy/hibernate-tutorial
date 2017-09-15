package com.StudentDemo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Student;

public class DeleteStudentDemo {

    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            System.out.println("Creating Student object...");
            session.beginTransaction();

            System.out.println("Saving the student.");
            List<Student> studentList = IntStream.rangeClosed(0, 10)
                    .mapToObj(i -> new Student("First Name" + i, "Last Name" + i, "first.last" + i + "@gmail.com"))
                    .peek(session::save).collect(Collectors.toList());

            System.out.println(studentList);

            session.getTransaction().commit();

            session = sessionFactory.getCurrentSession();

            session.beginTransaction();

            studentList.forEach(session::delete);

            session.getTransaction().commit();

            System.out.println("Done!");

        } finally {
            sessionFactory.close();
        }
    }
}
