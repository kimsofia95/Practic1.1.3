package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class UserDaoJDBCImpl implements UserDao {
    private Util util = Util.getInstance();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = util.DataBaseConnection(); Statement stmt = connection.createStatement()) {
            try {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), lastName VARCHAR(40), age INT(3))");
                connection.commit();
            }
            catch (Exception e) {
                e.printStackTrace();
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = util.DataBaseConnection(); Statement stmt = connection.createStatement()) {
            try {
                stmt.executeUpdate("DROP table IF EXISTS users");
                connection.commit();
            } catch (Exception e) {
                e.printStackTrace();
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = util.DataBaseConnection(); Statement stmt = connection.createStatement()) {
            try {
                stmt.executeUpdate("INSERT users(name, lastName, age) VALUES ('" + name + "', '" + lastName + "', " + age + ")");
                connection.commit();
            } catch (Exception e) {
                e.printStackTrace();
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.DataBaseConnection(); Statement stmt = connection.createStatement()) {
            try {
                stmt.executeUpdate("DELETE FROM users WHERE id IN ('" + id + "')");
                connection.commit();
            } catch (Exception e) {
                e.printStackTrace();
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try (Connection connection = util.DataBaseConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery("select * from users");) {
            while (rs.next()) {
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                Byte age = rs.getByte("age");
                usersList.add(new User(name, lastName, age));
            }
            return usersList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cleanUsersTable() {
        try (Connection connection = util.DataBaseConnection(); Statement stmt = connection.createStatement()) {
            try {
                stmt.executeUpdate("DELETE from users");
                connection.commit();
            } catch (Exception e) {
                e.printStackTrace();
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
