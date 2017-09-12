package com;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Student;

public class FindStudentByIDDemo {

    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            System.out.println("Saving the student.");
            Student student = new Student("First Name", "Last Name", "found.student@gmail.com");

            System.out.println("Creating Student object...");
            session.beginTransaction();
            session.save(student);
            session.getTransaction().commit();

            System.out.println("Saving Done! Student Id: " + student.getId() );

            session = sessionFactory.getCurrentSession();

            session.beginTransaction();

            System.out.println("Getting student by id.");
            Student foundStudent = session.get(Student.class, student.getId());

            session.getTransaction().commit();

            System.out.println("Student found: " + student);


        } finally {
            sessionFactory.close();
        }
    }
}
