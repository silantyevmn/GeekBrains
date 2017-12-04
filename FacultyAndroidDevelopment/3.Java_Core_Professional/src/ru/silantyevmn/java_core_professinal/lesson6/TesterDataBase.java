package ru.silantyevmn.java_core_professinal.lesson6;

import org.junit.*;
import java.sql.*;

/**
 * ru.silantyevmn.java_core_professinal.lesson6
 * Created by Михаил Силантьев on 04.12.2017.
 */
public class TesterDataBase {
    Connection connection;
    Statement statement;

    @Before
    public void connect() {
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

    @After
    public void disconnect() {
        try {
            statement.close();
            connection.rollback();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelete() throws SQLException {
        Assert.assertTrue(Lesson6.checkBase(statement,"DELETE FROM students;"));
    }
    @Test
    public void testInsert() throws SQLException {
        Assert.assertTrue(Lesson6.checkBase(statement,"INSERT INTO students(name, score) VALUES('студент1',99);"));
    }
    @Test
    public void testUpdate() throws SQLException {
        Assert.assertTrue(Lesson6.checkBase(statement,"UPDATE students SET score=80 WHERE name='студент2';"));
    }
    @Test
    public void testSelectTrue() throws SQLException {
        Assert.assertTrue(Lesson6.checkData(statement,"SELECT * FROM students WHERE score>5;"));
    }
    @Test
    public void testSelectFalse() throws SQLException {
        Assert.assertEquals(false,Lesson6.checkData(statement,"SELECT * FROM students WHERE score>100;"));
    }
}
