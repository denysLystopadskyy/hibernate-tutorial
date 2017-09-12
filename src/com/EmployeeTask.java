package com;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Employee;

public class EmployeeTask {

    private static volatile SessionFactory sessionFactory;

    public static void main(String[] args) {
        try {
            System.out.println("Creating employees");
            createEmployee();
        } finally {
            if (sessionFactory != null){
                sessionFactory.close();
            }
        }
    }

    private static void createEmployee() {
        Session session = getSessionWithTransaction();

        try {
            session.save(new Employee("FN1", "LN1", "Company"));
            session.save(new Employee("FN2", "LN2", "Company"));
        } finally {
            commitTransaction(session);
        }

    }

    private static void commitTransaction(Session session) {
        System.out.println("Commit Transaction.");
        session.getTransaction().commit();
        System.out.println("Close Session.");
        session.close();
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
        if (sessionFactory == null){
            System.out.println("Creating Session Factory");
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Employee.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

}
