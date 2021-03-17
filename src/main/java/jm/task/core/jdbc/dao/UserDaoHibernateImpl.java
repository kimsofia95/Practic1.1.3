package jm.task.core.jdbc.dao;

import com.mysql.cj.jdbc.BlobFromLocator;
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
        Session session = null;
        Transaction tx1 = null;
        try {
            session = sessionFactory.openSession();
            tx1 = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), lastName VARCHAR(40), age INT(3))").executeUpdate();
            tx1.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx1.rollback();
        }  finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        Transaction tx1 = null;
        try {
            session = sessionFactory.openSession();
            tx1 = session.beginTransaction();
            session.createSQLQuery("drop table IF EXISTS users").executeUpdate();
            tx1.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx1.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        Transaction tx1 = null;
        try {
            session = sessionFactory.openSession();
            tx1 = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tx1.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx1.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        Transaction tx1 = null;
        try {
            session = sessionFactory.openSession();
            User user = (User) session.get(User.class, id);
            tx1 = session.beginTransaction();
            session.delete(user);
            tx1.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx1.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = null;
        Transaction tx1 = null;
        try {
            session = sessionFactory.openSession();
            List<User> result = session.createQuery("From User").list();
            tx1 = session.beginTransaction();
            tx1.commit();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            tx1.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        Transaction tx1 = null;
        try {
            session = sessionFactory.openSession();
            tx1 = session.beginTransaction();
            session.createSQLQuery("delete from users").executeUpdate();
            tx1.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx1.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
