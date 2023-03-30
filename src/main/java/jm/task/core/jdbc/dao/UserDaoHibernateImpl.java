package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getHiberConnection().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS USERS (id BIGINT, Name VARCHAR(255), LastName VARCHAR(255), age TINYINT)").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getHiberConnection().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS USERS").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User man = new User(name, lastName, age);
        Transaction transaction = null;
        try (Session session = Util.getHiberConnection().openSession()) {
            transaction = session.beginTransaction();
            session.save(man);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("что-то не так");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        User man = new User();
        man.setId(id);
        Transaction transaction = null;
        try (Session session = Util.getHiberConnection().openSession()) {
            transaction = session.beginTransaction();
            session.remove(man);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> man = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = Util.getHiberConnection().openSession()) {
            transaction = session.beginTransaction();
            man = session.createQuery("from US", User.class).getResultList();
            transaction.commit();
            return man;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return man;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getHiberConnection().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("delete from users").executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
