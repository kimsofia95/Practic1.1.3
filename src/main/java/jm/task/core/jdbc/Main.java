package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;

import java.sql.*;

import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        User[] users = {new User("Masha", "Petrova", (byte) 20),  new User("Igor", "Alekseev", (byte) 25),
                new User("Sasha", "Familly", (byte) 29), new User("Kolya", "Lil", (byte) 23)};
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/study?useSSL=false&serverTimezone=Asia/Seoul";
            String login = "root";
            String password = "root";
            Connection con = Util.DataBaseConnection("com.mysql.cj.jdbc.Driver", url, login, password);

            String createTable = "CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), lastName VARCHAR(40), age INT(3))";
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate(createTable);
                for (int i = 0; i < users.length; i++) {
                    stmt.executeUpdate("INSERT users(name, lastName, age) VALUES ('" + users[i].getName() + "', '" + users[i].getLastName() + "', " + users[i].getAge() + ")");
                    System.out.println("User с именем - " + users[i].getName() + " добавлен в базу данных");
                }

                ResultSet rs = stmt.executeQuery("select * from users");
                while (rs.next()) {
                    String name = rs.getString("name") + " " + rs.getString("lastName");
                    String age = rs.getString("age");
                    System.out.println("Name: " + name + " Age: " + age);
                }
                stmt.executeUpdate("DELETE from users");
                stmt.executeUpdate("DROP table IF EXISTS users");
                rs.close();
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
