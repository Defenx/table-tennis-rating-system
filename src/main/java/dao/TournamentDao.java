package dao;

import entity.Extension;
import entity.Tournament;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@RequiredArgsConstructor
public class TournamentDao {
    private final SessionFactory sessionFactory;

    public void create(Tournament tournament) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            for (Extension extension : tournament.getExtensions()) {
                extension.setTournament(tournament);
            }
            session.persist(tournament);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

}
