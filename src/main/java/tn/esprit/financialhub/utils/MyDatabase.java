package tn.esprit.financialhub.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {
    private static MyDatabase instance;
    private final String URL = "jdbc:mysql://localhost:3306/testjava";
    private final String USER = "root";
    private final String PASSWORD = "123456";

    private static Connection connection;

    public MyDatabase() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("connection established");
        } catch (SQLException e) {
            System.err.println(e.getMessage());

        }

    }

    public static MyDatabase getInstance() {
        if (instance == null)
            instance = new MyDatabase();
        return instance;
    }

    public static Connection getConnection() {
        return connection;
    }

}


