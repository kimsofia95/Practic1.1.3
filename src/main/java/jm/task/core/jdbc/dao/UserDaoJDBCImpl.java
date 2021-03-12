package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Util util = Util.getInstance();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = util.DataBaseConnection(); Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), lastName VARCHAR(40), age INT(3))");
        } catch (SQLException e) {
            try {
                throw e;
            } catch (SQLException throwables) {
            }
        }
    }

    public void dropUsersTable() {
        try (Connection connection = util.DataBaseConnection(); Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DROP table IF EXISTS users");
        } catch (SQLException e) {
            try {
                throw e;
            } catch (SQLException throwables) {
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = util.DataBaseConnection(); Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("INSERT users(name, lastName, age) VALUES ('" + name + "', '" + lastName + "', " + age + ")");
        } catch (SQLException e) {
            try {
                throw e;
            } catch (SQLException throwables) {
            }
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.DataBaseConnection(); Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM users WHERE id IN ('" + id + "')");
        } catch (SQLException e) {
            try {
                throw e;
            } catch (SQLException throwables) {
            }
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
            try {
                throw e;
            } catch (SQLException throwables) {
            }
        }
        return null;
    }

    public void cleanUsersTable() {
        try (Connection connection = util.DataBaseConnection(); Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE from users");
        } catch (SQLException e) {
            try {
                throw e;
            } catch (SQLException throwables) {
            }
        }
    }
}
