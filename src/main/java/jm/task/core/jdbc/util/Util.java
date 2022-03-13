package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/JAVA_DB";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "540880Zaq";

    public Connection getConnection() {
        Connection connection = null;
        try {
            // подключение библиотеки к нашему java приложению
            Class.forName(DB_DRIVER);

            // /JAVA_DB - название нашей базы данных
            // A connection (session) with a specific database. SQL statements are executed and results are returned
            // within the context of a connection.
            connection = DriverManager.getConnection(DB_URL,
                    DB_USERNAME, DB_PASSWORD);
            System.out.println("Connection OK...!!!");
        } catch(ClassNotFoundException | SQLException e) {
            System.out.println("Connection failed...!!!");
            e.printStackTrace();
        }
        return connection;
    }

}
