package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final UserDaoHibernateImpl INSTANCE = new UserDaoHibernateImpl();
    private static final SessionFactory sessionFactory = Util.getSessionFactory();

    private UserDaoHibernateImpl() {

    }

    public static UserDaoHibernateImpl getInstance() {
        return INSTANCE;
    }

    public void createUsersTable() {
        String CREATE_TABLE_MySQL = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT AUTO_INCREMENT PRIMARY KEY, user_name VARCHAR(50), " +
                "user_lastName VARCHAR(50), user_age TINYINT)";

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createNativeQuery(CREATE_TABLE_MySQL).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        String DROP_TABLE_MySQL = "DROP TABLE IF EXISTS users";

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createNativeQuery(DROP_TABLE_MySQL).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.persist(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String REMOVE_USER_FROM_TABLE_MySQL = "DELETE FROM users WHERE id = ?";

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createNativeQuery(REMOVE_USER_FROM_TABLE_MySQL).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            usersList = session.createQuery("FROM User", User.class).list();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }

        return usersList;
    }

    public void cleanUsersTable() {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
            } catch (HibernateException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
