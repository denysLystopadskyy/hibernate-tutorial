package com;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Employee;

public class EmployeeTask {

    private static volatile SessionFactory sessionFactory;

    public static void main(String[] args) {
        try {
            System.out.println("Creating employees");
            Employee employee = createEmployee();
            updateEmployee();
            deleteEmployee();
            findEmployee();

            deleteEmployeeByID(employee.getId());
        } finally {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }

    private static void deleteEmployeeByID(int id) {
        try (Session sessionWithTransaction = getSessionWithTransaction()) {
            sessionWithTransaction.createQuery(String.format("DELETE Employee Where id = %d", id)).executeUpdate();
            commitTransaction(sessionWithTransaction);
        }
    }

    private static void findEmployee() {
        try (Session sessionWithTransaction = getSessionWithTransaction()) {
            List<Employee> employees = sessionWithTransaction.createQuery("FROM Employee e WHERE e.firstName='Updated'").getResultList();
            commitTransaction(sessionWithTransaction);
            employees.forEach(System.out::println);
        }
    }

    private static Employee deleteEmployee() {
        Employee employee = createEmployee();
        System.out.println("Should be deleted: " + employee);
        try (Session sessionWithTransaction = getSessionWithTransaction()) {
            sessionWithTransaction.delete(employee);
            commitTransaction(sessionWithTransaction);
        }
        return null;
    }

    private static Employee updateEmployee() {
        Employee employee = createEmployee();

        try (Session sessionWithTransaction = getSessionWithTransaction()) {
            employee.setFirstName("Updated");
            sessionWithTransaction.update(employee);
            commitTransaction(sessionWithTransaction);
            return employee;
        }
    }

    private static Employee createEmployee() {
        Session session = getSessionWithTransaction();

        try {
            long currentTimeMillis = System.currentTimeMillis();
            Employee employee = new Employee("FN1" + currentTimeMillis, "LN1" + currentTimeMillis, "Company");
            session.save(employee);
            return employee;
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
        if (sessionFactory == null) {
            System.out.println("Creating Session Factory");
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Employee.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

}
