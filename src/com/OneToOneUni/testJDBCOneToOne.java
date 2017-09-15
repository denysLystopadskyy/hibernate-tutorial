package com.OneToOneUni;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class testJDBCOneToOne {

    public static void main(String[] args) {
        String jdbcurl = "jdbc:mysql://localhost:3306/hb-01-one-to-one-uni?useSSL=false";

        String user ="hbstudent";
        String pass ="hbstudent";

        System.out.println("Connecting to DB.");
        try (Connection connection = DriverManager.getConnection(jdbcurl, user, pass)) {
            System.out.println("Connection established!");
        } catch (SQLException e) {
            System.out.println("Something wrong, i can fill it.");
            System.out.println(e.getMessage());
        }

    }

}
