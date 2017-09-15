package com.OneToOneUni;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Instructor;
import com.entity.InstructorDetail;

public class InstructorDemo {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        try {
            createInstructor();
        } catch (Exception e) {
            System.out.println("Error: => " + e.getMessage());
            throw e;
        } finally {
            sessionFactory.close();
        }

    }

    private static Instructor createInstructor() {
        try (Session sessionWithTransaction = getSessionWithTransaction()) {
            Instructor instructor = new Instructor("Last Name", "First Name", "email@gmail.com");
            instructor.setInstructorDetail(new InstructorDetail("test@channel.com", "Test Hobby"));
            sessionWithTransaction.save(instructor);
            commitTransaction(sessionWithTransaction);
            System.out.println(instructor);
            return instructor;
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private static Session getSessionWithTransaction() {
        System.out.println("Retrieving session.");
        Session session = getSessionFactory().getCurrentSession();
        System.out.println("Begin transaction.");
        session.beginTransaction();
        return session;
    }

    private static SessionFactory getSessionFactory() {
        System.out.println("Retrieving session Factory.");
        if (sessionFactory == null) {
            System.out.println("Creating Session Factory");
            sessionFactory = new Configuration()
                    .configure("hibernateOneToOneUni.cfg.xml")
                    .addAnnotatedClass(Instructor.class)
                    .addAnnotatedClass(InstructorDetail.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    private static void commitTransaction(Session session) {
        System.out.println("Commit Transaction.");
        session.getTransaction().commit();
        System.out.println("Close Session.");
        session.close();
    }

}
