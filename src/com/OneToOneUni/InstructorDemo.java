package com.OneToOneUni;

import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.InstructorUni;
import com.entity.InstructorDetailUni;

public class InstructorDemo {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        try {
            createInstructor();
            deleteInstructor();
            deleteInstructorDetails();
        } catch (Exception e) {
            System.out.println("Error: => " + e.getMessage());
            throw e;
        } finally {
            sessionFactory.close();
        }

    }

    private static void deleteInstructor() {
        InstructorUni instructorUni = createInstructor();
        System.out.println(instructorUni);

        try (Session sessionWithTransaction = getSessionWithTransaction()) {
            sessionWithTransaction.delete(instructorUni);
            commitTransaction(sessionWithTransaction);
        }
    }

    private static void deleteInstructorDetails() {
        InstructorUni instructorUni = createInstructor();
        InstructorDetailUni instructorDetailUni = instructorUni.getInstructorDetailUni();
        System.out.println(instructorDetailUni);

        try (Session sessionWithTransaction = getSessionWithTransaction()) {
            sessionWithTransaction.delete(instructorDetailUni);
            commitTransaction(sessionWithTransaction);
        }catch (Exception e){
            if (e instanceof PersistenceException){
                System.out.println("Everything is fine! you can not delete it.");
            }else {
                throw e;
            }
        }
    }

    private static InstructorUni createInstructor() {
        try (Session sessionWithTransaction = getSessionWithTransaction()) {
            InstructorUni instructorUni = new InstructorUni("Last Name", "First Name", "email@gmail.com");
            instructorUni.setInstructorDetailUni(new InstructorDetailUni("test@channel.com", "Test Hobby"));
            sessionWithTransaction.save(instructorUni);
            commitTransaction(sessionWithTransaction);
            System.out.println(instructorUni);
            return instructorUni;
        } catch (Exception e) {
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
                    .addAnnotatedClass(InstructorUni.class)
                    .addAnnotatedClass(InstructorDetailUni.class)
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
