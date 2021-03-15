package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javax.persistence.Entity;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class UserDaoHibernateImpl implements UserDao {
    public SessionFactory sessionFactory = Util.createHibernateSession();
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try {
            Session session = sessionFactory.openSession();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), lastName VARCHAR(40), age INT(3))").executeUpdate();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            sessionFactory.getCurrentSession().getTransaction().rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = sessionFactory.openSession();
            session.createSQLQuery("drop table IF EXISTS users").executeUpdate();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            sessionFactory.getCurrentSession().getTransaction().rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = sessionFactory.openSession();
            Transaction tx1 = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tx1.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            sessionFactory.getCurrentSession().getTransaction().rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = sessionFactory.openSession();
            User user = (User) session.get(User.class, id);
            Transaction tx1 = session.beginTransaction();
            session.delete(user);
            tx1.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            sessionFactory.getCurrentSession().getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            Session session = sessionFactory.openSession();
            List<User> result = session.createQuery("From User").list();
            session.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            sessionFactory.getCurrentSession().getTransaction().rollback();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = sessionFactory.openSession();
            List<User> users = getAllUsers();
            Transaction tx1 = session.beginTransaction();
            for (User user : users) {
                session.delete(user);
            }
            tx1.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            sessionFactory.getCurrentSession().getTransaction().rollback();
        }
    }
}
