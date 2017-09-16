package com.OneToOneBi;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.InstructorBi;
import com.entity.InstructorDetailBi;

public class InstructorDemoBi {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        try {
//            createInstructor();
//            deleteInstructor();
            deleteInstructorDetails();
//            findInstructorDeatil();
        } catch (Exception e) {
            System.out.println("Error: => " + e.getMessage());
            throw e;
        } finally {
            sessionFactory.close();
        }

    }

    private static void findInstructorDeatil() {
        InstructorBi instructor = createInstructor();

        try (Session sessionWithTransaction = getSessionWithTransaction()) {
            InstructorDetailBi instructorDetailBi = sessionWithTransaction.find(InstructorDetailBi.class, instructor.getInstructorDetailBi().getId());

            InstructorBi biInstructor = instructorDetailBi.getInstructor();
            System.out.println(biInstructor);
        }
    }

    private static void deleteInstructor() {
        InstructorBi instructorBi = createInstructor();
        System.out.println(instructorBi);

        try (Session sessionWithTransaction = getSessionWithTransaction()) {
            sessionWithTransaction.delete(instructorBi);
            commitTransaction(sessionWithTransaction);
        }
    }

    private static void deleteInstructorDetails() {
        InstructorBi instructorBi = createInstructor();

        try (Session sessionWithTransaction = getSessionWithTransaction()) {

            InstructorDetailBi instructorDetailBi = sessionWithTransaction.find(InstructorDetailBi.class, instructorBi.getInstructorDetailBi().getId());
            System.out.println(instructorDetailBi);

            //breaking Bi Reference
            instructorDetailBi.getInstructor().setInstructorDetail(null);

            sessionWithTransaction.delete(instructorDetailBi);

            commitTransaction(sessionWithTransaction);
        }
    }

    private static InstructorBi createInstructor() {
        try (Session sessionWithTransaction = getSessionWithTransaction()) {
            InstructorBi instructor = new InstructorBi("Last Name", "First Name", "email@gmail.com");
            instructor.setInstructorDetail(new InstructorDetailBi("test@channel.com", "Test Hobby"));
            sessionWithTransaction.save(instructor);
            commitTransaction(sessionWithTransaction);
            System.out.println(instructor);
            return instructor;
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
                    .configure("hibernateOneToOneBi.cfg.xml")
                    .addAnnotatedClass(InstructorBi.class)
                    .addAnnotatedClass(InstructorDetailBi.class)
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
