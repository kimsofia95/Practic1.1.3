package jm.task.core.jdbc.service;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private String url = "jdbc:mysql://127.0.0.1:3306/study?useSSL=false&serverTimezone=Asia/Seoul";
    private String login = "root";
    private String password = "root";
    private Connection connection = Util.DataBaseConnection("com.mysql.cj.jdbc.Driver", this.url, this.login, this.password);

    public void createUsersTable() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), lastName VARCHAR(40), age INT(3))");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DROP table IF EXISTS users");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("INSERT users(name, lastName, age) VALUES ('" + name + "', '" + lastName + "', " + age + ")");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM users WHERE id IN ('" + id + "')");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from users");
            while (rs.next()) {
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                Byte age = rs.getByte("age");
                usersList.add(new User(name, lastName, age));
            }
            return usersList;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    public void cleanUsersTable() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE from users");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
