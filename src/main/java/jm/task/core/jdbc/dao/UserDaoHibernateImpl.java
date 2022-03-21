package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory factory;

    public UserDaoHibernateImpl() {
        this.factory = new Util().getFactory();
    }

    @Override
    public void createUsersTable() {
        System.out.println("Creating table...");
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("create table if not exists USERS_TABLE (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255) NOT NULL, lastname VARCHAR(255) NOT NULL, age INT NOT NULL)").executeUpdate();
            session.getTransaction().commit();
        }
        System.out.println("Created table in given database...OK");
    }

    @Override
    public void dropUsersTable() {
        System.out.println("Deleting table...");
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("drop table if exists USERS_TABLE").executeUpdate();
            session.getTransaction().commit();
        }
        System.out.println("Table deleted...OK");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        System.out.println("Creating user...");
        try (Session session = factory.getCurrentSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        System.out.println("Removing user by ID");
        try (Session session = factory.getCurrentSession();) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            System.out.println("User " + user + " deleted...");
            session.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            System.out.println("!!!WARNING!!! Attempt to delete null object");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        System.out.println("Getting all users...");
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            users = session.createQuery("from User").getResultList(); // указываем не таблицу в БД, а класс, который представляет из себя поле таблицы
            session.getTransaction().commit();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        System.out.println("Cleaning Users Table...");
        try (Session session = factory.getCurrentSession();) {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
