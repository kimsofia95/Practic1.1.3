package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import java.sql.*;
import java.util.List;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        User[] users = {new User("Masha", "Petrova", (byte) 20),  new User("Igor", "Alekseev", (byte) 25),
                new User("Sasha", "Familly", (byte) 29), new User("Kolya", "Lil", (byte) 23)};
        UserService userService = new UserServiceImpl();
        try {
            userService.createUsersTable();
            for (int i = 0; i < users.length; i++) {
                userService.saveUser(users[i].getName(), users[i].getLastName(), users[i].getAge());
                System.out.println("User с именем - " + users[i].getName() + " добавлен в базу данных");
            }
            List<User> listUsers = userService.getAllUsers();
            for (User user : listUsers) {
                System.out.println("Name: " + user.getName() + " " + user.getLastName() + " Age: " + user.getAge());
            }

            userService.cleanUsersTable();
            userService.dropUsersTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
