package dao;

import entity.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserDao {
    private final SessionFactory sessionFactory;

    public User findById(UUID id) {
        User user;
        try (Session session = sessionFactory.openSession()) {
            user = session.find(User.class, id);
        } catch (HibernateException he) {
            throw new RuntimeException(he);
        }

        return user;
    }

    public List<User> findAllOrderByRatingAsc() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User u ORDER BY rating DESC", User.class);
            return query.getResultList();
        } catch (HibernateException he) {
            throw new RuntimeException(he);
        }
    }

    public void deleteById(UUID id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            MutationQuery mutationQuery = session.createMutationQuery("DELETE FROM User u WHERE id = :id");
            mutationQuery.setParameter("id", id);
            mutationQuery.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void create(User user) {
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

    public Optional<User> findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            return Optional.ofNullable(query.uniqueResult());
        } catch (HibernateException he) {
            he.printStackTrace();
            throw new RuntimeException(he);
        }
    }
}
