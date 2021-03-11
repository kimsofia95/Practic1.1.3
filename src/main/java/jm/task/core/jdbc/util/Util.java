package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

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

    public static Session createHibernateSession() {
        SessionFactory sessionFactory = null;
        ServiceRegistry serviceRegistry = null;
        Session session = null;
        try {
            Properties prop = new Properties();
            prop.setProperty("connection.driver_class", "com.mysql.cj.jdbc.Driver");
            prop.setProperty("hibernate.connection.url", "jdbc:mysql://127.0.0.1:3306/study?useSSL=false&serverTimezone=Asia/Seoul");
            prop.setProperty("hibernate.connection.username", "root");
            prop.setProperty("hibernate.connection.password", "root");
            prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
            prop.setProperty("hibernate.show_sql", "true");
            prop.setProperty("hibernate.format_sql", "true");
            Configuration cfg = new Configuration();
            cfg.addAnnotatedClass(User.class);
            cfg.setProperties(prop);
            serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(cfg.getProperties()).build();
            sessionFactory = cfg.buildSessionFactory(serviceRegistry);
            session = sessionFactory.openSession();
        } catch (Exception e) {
        }
        return session;
    }
}
