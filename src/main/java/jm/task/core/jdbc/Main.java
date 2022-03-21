package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import javax.persistence.EntityManager;
import java.util.List;

// ?? как правильно и когда закрывать connection и statement?
// стоит ли использовать в UserDaoJDBCImpl - try с ресурсами?

public class Main {
    public static void main(String[] args) {

        UserService us = new UserServiceImpl();
        us.createUsersTable();
        us.saveUser("Gregory", "Leps", (byte) 42);
        us.saveUser("Ivan", "Vasilievich", (byte) 45);
        us.saveUser("Koshey", "Bessmertniy", (byte) 102);
        us.removeUserById(2);
        List<User> usersList = us.getAllUsers();
        usersList.stream().forEach(System.out::println);
        us.cleanUsersTable();
        us.dropUsersTable();
        Util.getSessionFactoryToClose().close();
    }
}

/*
        Описание задачи:

        В прошлой задаче мы познакомились с Maven и JDBC, доработали приложение, взаимодействующее с базой данных.
        На этот раз обратим внимание на класс UserHibernateDaoImpl, который реализует тот же интерефейс, что
        и UserDaoJdbcImpl.

        В рамках этой задачи необходимо реализовать взаимодействие с базой данных с помощью Hibernate и
        дописать методы в классе UserHibernateDaoImpl, проверить свои методы заранее написанными JUnit
        тестами из заготовки.

        Требования к классам приложения:

        1. UserHibernateDaoImpl должен реализовывать интерефейс UserDao
        2. В класс Util должна быть добавлена конфигурация для Hibernate ( рядом с JDBC), без использования xml.
        3. Service на этот раз использует реализацию dao через Hibernate
        4. Методы создания и удаления таблицы пользователей в классе UserHibernateDaoImpl должны быть реализованы с помощью SQL.


        Алгоритм приложения и операции не меняются в сравнении с предыдущим заданием.
 */