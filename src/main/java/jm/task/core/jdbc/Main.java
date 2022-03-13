package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

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
    }
}

/*
        Описание задачи:

        Необходимо ознакомиться с заготовкой и доработать приложение, которое взаимодействует с базой оперируя
        пользователем ( класс User ) и проверить свои методы заранее написанными JUnit тестами.
        По итогу все тесты должны быть пройдены. Разрешается посмотреть реализацию тестов.

        Для запуска теста необходимо найти класс в папке test ( показано в предыдущей лекции )
        и при нажатии на него правой кнопкой мыши запустить, выбрав Run "Имя класса"

        Класс UserHibernateDaoImpl в рамках этой задачи не затрагивается (остаётся пустой)

        User представляет из себя сущность с полями:

        Long id
        String name
        String lastName
        Byte age

         Архитектура приложения создана с опорой на паттерн проектирования MVC ( частично, у нас не WEB приложение)
         Ознакомиться с паттерном можно здесь
         https://pro-java.ru/patterny-proektirovaniya-java/chto-takoe-pattern-proektirovaniya-mvc-v-java/


        Требования к классам приложения:

     1. Классы dao/service должны реализовывать соответствующие интерфейсы
     2. Класс dao должен иметь конструктор пустой/по умолчанию
     3. Все поля должны быть private
     4. service переиспользует методы dao
     5. Обработка всех исключений, связанных с работой с базой данных должна находиться в dao
     6. Класс Util должен содержать логику настройки соединения с базой данных


        Необходимые операции:

     1. Создание таблицы для User(ов) – не должно приводить к исключению, если такая таблица уже существует
     2. Удаление таблицы User(ов) – не должно приводить к исключению, если таблицы не существует
     3. Очистка содержания таблицы
     4. Добавление User в таблицу
     5. Удаление User из таблицы ( по id )
     6. Получение всех User(ов) из таблицы


        Алгоритм работы приложения:
        В методе main класса Main должны происходить следующие операции:

     1. Создание таблицы User(ов)
     2. Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
     3. Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
     4. Очистка таблицы User(ов)
     5. Удаление таблицы
 */