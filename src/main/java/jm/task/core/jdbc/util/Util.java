package jm.task.core.jdbc.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    public static Connection DataBaseConnection(String className, String url, String login, String password) {
        try {
            Class.forName(className).getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection(url, login, password);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
