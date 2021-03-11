package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.Entity;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = Util.createHibernateSession();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), lastName VARCHAR(40), age INT(3))").executeUpdate();
        session.close();
        session.getSessionFactory().close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.createHibernateSession();
        session.createSQLQuery("drop table IF EXISTS users").executeUpdate();
        session.close();
        session.getSessionFactory().close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.createHibernateSession();
        Transaction tx1 = session.beginTransaction();
        session.save(new User(name, lastName, age));
        tx1.commit();
        session.close();
        session.getSessionFactory().close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.createHibernateSession();
        User user = (User) session.get(User.class, id);
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
        session.getSessionFactory().close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.createHibernateSession();
        List<User> result = session.createQuery("From User").list();
        session.close();
        session.getSessionFactory().close();
        return result;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.createHibernateSession();
        List<User> users = getAllUsers();
        Transaction tx1 = session.beginTransaction();
        for (User user : users) {
            session.delete(user);
        }
        tx1.commit();
        session.close();
        session.getSessionFactory().close();
    }
}
