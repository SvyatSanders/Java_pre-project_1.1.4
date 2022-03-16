package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/JAVA_DB";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "540880Zaq";

    /**
     * получаем Factory для соединения с БД с помощью Hibernate
     */
    public SessionFactory getFactory() {
        // конфигурация Hibernate через .xml файл
        // return new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class).buildSessionFactory();

        /**
         * конфигурируем Hibernate с помощью Java
         * https://dzone.com/articles/hibernate-5-java-configuration-example
         */
        SessionFactory sessionFactory = null;
        try {
            Configuration configuration = new Configuration();
            //The Properties class represents a persistent set of properties.
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            properties.put(Environment.URL, "jdbc:mysql://localhost:3306/JAVA_DB?useSSL=false");
            properties.put(Environment.USER, "root");
            properties.put(Environment.PASS, "540880Zaq");
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            // Environment.HBM2DDL_AUTO - меняет столбцы местами?? по алфавиту??
//            settings.put(Environment.HBM2DDL_AUTO, "create-drop");

            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);

            // ServiceRegistry holds the services that Hibernate will need during bootstrapping and at runtime.
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }

    /**
     * получаем Connection для соединения с БД с помощью JDBC
     */
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
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection failed...!!!");
            e.printStackTrace();
        }
        return connection;
    }

}
