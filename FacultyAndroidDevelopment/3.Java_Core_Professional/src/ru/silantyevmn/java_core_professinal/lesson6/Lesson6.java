package ru.silantyevmn.java_core_professinal.lesson6;

import java.sql.*;
import java.util.*;

/**
 * ru.silantyevmn.java_core_professinal.lesson6
 * Created by Михаил Силантьев on 04.12.2017.
 * <p>
 * 1. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив,
 * метод должен вернуть новый массив, который получен путем вытаскивания элементов из исходного массива,
 * идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку, в противном случае в методе необходимо выбросить RuntimeException.
 * Написать набор тестов для этого метода (варианта 3-4 входных данных)
 * вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ]
 * <p>
 * 2. Написать метод, который проверяет что массив состоит только из чисел 1 и 4. Если в массиве нет хоть одной 4 или 1, то метод вернет false;
 * Написать набор тестов для этого метода (варианта 3-4 входных данных)
 * <p>
 * 3. Создать небольшую БД (таблица: студенты; поля: id, фамилия, балл).
 * Написать тесты для проверки того, что при работе с базой корректно добавляются, обновляются и читаются записи.
 * Следует учесть что в базе есть заранее добавленные записи, и после проведения тестов эти записи не должны быть удалены/изменены/добавлены.
 */
public class Lesson6 {
    private static Connection connection;
    private static Statement statement;

    public static void main(String[] args) {
        //1
        System.out.println(Arrays.toString(metod1(new int[]{1, 2, 3, 5, 4, 3, 4, 1, 1}, 4)));
        //2
        System.out.println(metod2(new int[]{4, 2, 3, 5, 6, 7, 8, 1}, 4, 1));
        //3
        try {
            connect(); //подключение к бд
            initTable(); //создание Таблицы, если есть обнуляем ее
            addData(); // добавляем данные
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect(); //разрываем все подключения
        }
        // тестируем базу в TesterDataBase
    }

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:sqlBase.db");
            statement = connection.createStatement();
            connection.setAutoCommit(false);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            statement.close();
            connection.rollback();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initTable() throws SQLException {
        boolean isTable = statement.execute("CREATE TABLE IF NOT EXISTS students(id INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "name TEXT NOT NULL, score INTEGER NOT NULL);");
        if (!isTable) {
            statement.execute("DELETE FROM students;");
        }
    }

    private static void addData() throws SQLException {
        connection.setAutoCommit(false);
        PreparedStatement ps = connection.prepareStatement("INSERT INTO students(name, score) VALUES (?,?);");
        for (int i = 1; i <= 5; i++) {
            ps.setString(1, "сотрудник" + i);
            ps.setInt(2, (i * 2));
            ps.executeUpdate();
        }
        connection.commit();
    }

    public static boolean checkBase(Statement statement, String text) {
        int i = 0;
        try {
            i = statement.executeUpdate(text);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i != 0;

    }

    public static boolean checkData(Statement statement, String text) {
        boolean isNext = false;
        try {
            ResultSet result = statement.executeQuery(text);
            isNext = result.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isNext;
    }


    public static boolean metod2(int[] arrs, int value1, int value2) {
        boolean[] res = new boolean[2];
        for (int i = 0; i < arrs.length; i++) {
            if (arrs[i] == value1) {
                res[0] = true;
            }
            if (arrs[i] == value2) {
                res[1] = true;
            }
        }
        return res[0] && res[1];
    }

    public static int[] metod1(int[] arrs, int value) throws RuntimeException {
        for (int i = arrs.length - 1; i >= 0; i--) {
            if (arrs[i] == value) {
                int[] out = new int[arrs.length - i - 1];
                System.arraycopy(arrs, i + 1, out, 0, out.length);
                return out;
            }
        }
        throw new RuntimeException("В массиве нет " + value + "!");
    }
}
