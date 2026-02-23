package com.finpay3;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/finpay_db";
    private static final String USER = "root";
    private static final String PASSWORD = "123454321";
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finpay_db", "root", "123454321");
        } catch (SQLException e) {
            System.err.println("Connection failed! " + e.getMessage());
            e.printStackTrace();
        }

        return connection;
    }
}

