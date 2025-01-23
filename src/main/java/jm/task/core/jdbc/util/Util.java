package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
//    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/new_schema";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static Connection getConnection() {
//        Class.forName(JDBC_DRIVER);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}
