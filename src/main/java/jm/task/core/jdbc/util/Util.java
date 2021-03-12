package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Util {
    private static Util sInstance = null;
    private final static String url = "jdbc:mysql://127.0.0.1:3306/study?useSSL=false&serverTimezone=Asia/Seoul";
    private final static String login = "root";
    private final static String password = "root";
    private final static String className = "com.mysql.cj.jdbc.Driver";

    private Util() {
    }

    public static Util getInstance() {
        if (sInstance == null) {
            sInstance = new Util();
        }
        return sInstance;
    }

    public static Connection DataBaseConnection() {
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
            prop.setProperty("connection.driver_class", className);
            prop.setProperty("hibernate.connection.url", url);
            prop.setProperty("hibernate.connection.username", login);
            prop.setProperty("hibernate.connection.password", password);
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
            e.printStackTrace();
        }
        return session;
    }
}
