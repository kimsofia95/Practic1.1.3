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
    private Util util = Util.getInstance();
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try {
            SessionFactory sessionFactory = util.createHibernateSession();
            Session session = sessionFactory.openSession();
            try {
                session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), lastName VARCHAR(40), age INT(3))").executeUpdate();
            } catch (RuntimeException e) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            } finally {
                session.close();
                sessionFactory.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            SessionFactory sessionFactory = util.createHibernateSession();
            Session session = sessionFactory.openSession();
            try {
                session.createSQLQuery("drop table IF EXISTS users").executeUpdate();
            } catch (RuntimeException e) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }
            finally {
                session.close();
                sessionFactory.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            SessionFactory sessionFactory = util.createHibernateSession();
            Session session = sessionFactory.openSession();
            try {
                Transaction tx1 = session.beginTransaction();
                session.save(new User(name, lastName, age));
                tx1.commit();
            } catch (RuntimeException e) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }
            finally {
                session.close();
                sessionFactory.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            SessionFactory sessionFactory = util.createHibernateSession();
            Session session = sessionFactory.openSession();
            User user = (User) session.get(User.class, id);
            try {
                Transaction tx1 = session.beginTransaction();
                session.delete(user);
                tx1.commit();
            } catch (RuntimeException e) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }
            finally {
                session.close();
                sessionFactory.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            SessionFactory sessionFactory = util.createHibernateSession();
            Session session = sessionFactory.openSession();
            try {
                List<User> result = session.createQuery("From User").list();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                session.close();
                sessionFactory.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try {
            SessionFactory sessionFactory = util.createHibernateSession();
            Session session = sessionFactory.openSession();
            List<User> users = getAllUsers();
            try {
                Transaction tx1 = session.beginTransaction();
                for (User user : users) {
                    session.delete(user);
                }
                tx1.commit();
            } catch (RuntimeException e) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }
            finally {
                session.close();
                sessionFactory.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
