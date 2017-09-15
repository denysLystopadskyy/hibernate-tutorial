package com.StudentDemo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Student;

public class QueryStudentDemo {

    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            System.out.println("Creating Student object...");
            session.beginTransaction();

//            System.out.println("Saving the student.");
//            List<Student> studentList = IntStream.rangeClosed(0, 10)
//                    .mapToObj(i -> new Student("First Name", "Last Name" + System.currentTimeMillis(), "first.last" + i + "@gmail.com"))
//                    .peek(session::save).collect(Collectors.toList());

            session.getTransaction().commit();

            session = sessionFactory.getCurrentSession();

            session.beginTransaction();

            List<Student> students = session.createQuery("FROM Student s WHERE s.firstName = 'First Name'").list();

            System.out.println("First Name students");
            printStudents(students);

            students = session.createQuery("FROM Student s WHERE s.firstName = 'First Name' OR s.email LIKE '%gmail%'").list();

            System.out.println("First Name OR %gmail% students");
            printStudents(students);

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
