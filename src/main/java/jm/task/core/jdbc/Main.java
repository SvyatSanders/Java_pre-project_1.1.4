package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        User user = new User();

        try {
            // подключение библиотеки к нашему java приложению
            Class.forName("com.mysql.cj.jdbc.Driver");

            // /JAVA_DB - название нашей базы данных
            // A connection (session) with a specific database. SQL statements are executed and results are returned
            // within the context of a connection.
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAVA_DB",
                    "root", "540880Zaq");
            System.out.println("Мы подключились...!!!");

            // создаем statement - the object used for executing a static SQL statement and returning the results it produces.
            Statement stmnt = conn.createStatement();

            // 1. Создание таблицы User(ов)
            int createDB = stmnt.executeUpdate("create table if not exists USERS_TABLE (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255) NOT NULL, lastname VARCHAR(255) NOT NULL, age INT NOT NULL)");
            System.out.println("Created table in given database..." + createDB);

            // 2. Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод
            // в консоль ( User с именем – name добавлен в базу данных )
            String SQLUsers = "insert into USERS_TABLE (name, lastname, age) values (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(SQLUsers);

            ps.setString(1, "Григорий");
            ps.setString(2, "Лепс");
            ps.setInt(3, 42);
            System.out.println("User с именем – \"Григорий\" добавлен в базу данных " + ps.executeUpdate());

            ps.setString(1, "Василий");
            ps.setString(2, "Иванов");
            ps.setInt(3, 24);
            System.out.println("User с именем – \"Василий\" добавлен в базу данных " + ps.executeUpdate());

            ps.setString(1, "Иван");
            ps.setString(2, "Грозный");
            ps.setInt(3, 35);
            System.out.println("User с именем – \"Иван\" добавлен в базу данных " + ps.executeUpdate());

            ps.setString(1, "Кощей");
            ps.setString(2, "Бессмертный");
            ps.setInt(3, 100);
            System.out.println("User с именем – \"Кощей\" добавлен в базу данных " + ps.executeUpdate());

            // 3. Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
            ResultSet rs = stmnt.executeQuery("select * from USERS_TABLE");
            List<User> usersList = new ArrayList<>();
            while (rs.next()) {
                User userTemp = new User(rs.getString("name"), rs.getString("lastName"), rs.getByte("age"));
                usersList.add(userTemp);
            }
            usersList.stream().forEach(System.out::println);

            // 4. Очистка таблицы User(ов)
            int truncateDB = stmnt.executeUpdate("truncate users_table");
            System.out.println("Table truncated...");

            // 5. Удаление таблицы
            int deleteDB = stmnt.executeUpdate("DROP TABLE IF EXISTS users_table");
            System.out.println("Table deleted...");

            stmnt.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection failed...!!!");
            e.printStackTrace();
        }
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