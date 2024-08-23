package dao;

import entity.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Optional;
import java.util.UUID;

/**
 * The type User dao.
 */
@RequiredArgsConstructor
public class UserDao {
    private final SessionFactory sessionFactory;

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    public User getById(UUID id) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            user = session.get(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Create.
     *
     * @param user the user
     */
    public void create(User user) throws HibernateException {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    /**
     * Find by email and password optional.
     *
     * @param email    the email
     * @param password the password
     * @return the optional
     */
    public Optional<User> findByEmailAndPassword(String email, String password) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        User user = null;
        try {
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery("FROM User u WHERE u.email = :email AND u.password = :password", User.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            user = query.uniqueResult();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return user != null ? Optional.of(user) : Optional.empty();
    }
}
