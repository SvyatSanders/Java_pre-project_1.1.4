package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;
    private Statement statement;

    public UserDaoJDBCImpl() { // конструктор, получающий Connection из Util
        this.connection = new Util().getConnection();
    }

    public void createUsersTable() {
        try {
            statement = connection.createStatement();
            statement.executeUpdate("create table if not exists USERS_TABLE (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255) NOT NULL, lastname VARCHAR(255) NOT NULL, age INT NOT NULL)");
            System.out.println("Created table in given database...OK");
        } catch (SQLException e) {
            System.out.println("Table creation failed...!!!");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {

        try {
            statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS users_table");
            System.out.println("Table deleted...");
        } catch (SQLException e) {
            System.out.println("Drop table failed...!!!");
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String SQLUsers = "insert into USERS_TABLE (name, lastname, age) values (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(SQLUsers);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных ");
        } catch (SQLException e) {
            System.out.println("User save failed...!!!");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String SQLUsers = "DELETE FROM USERS_TABLE WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQLUsers);
            ps.setLong(1, id);
            ps.executeUpdate();
            System.out.println("User №" + id + " удален из базы данных ");
        } catch (SQLException e) {
            System.out.println("User delete failed...!!!");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();

        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from USERS_TABLE");
            while (rs.next()) {
                User userTemp = new User(rs.getString("name"), rs.getString("lastName"), rs.getByte("age"));
                usersList.add(userTemp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usersList;
    }

    public void cleanUsersTable() {
        try {
            statement = connection.createStatement();
            statement.executeUpdate("truncate users_table");
            System.out.println("Table cleaned...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
