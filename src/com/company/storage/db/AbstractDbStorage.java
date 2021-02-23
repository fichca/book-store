package com.company.storage.db;


import java.sql.*;

public abstract class AbstractDbStorage {

    protected static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    protected static final String USERNAME = "postgres";
    protected static final String PASSWORD = "root";
    protected static Connection connection;


    static {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
