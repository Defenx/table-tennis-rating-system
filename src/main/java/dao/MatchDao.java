package dao;

import entity.Match;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class MatchDao {
    private final SessionFactory sessionFactory;


    public void addMatches(List<Match> matches) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            for (Match match : matches) {
                session.persist(match);
            }

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<Match> getMatchesByTournamentId(UUID tournamentId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Match> query = session.createQuery("FROM Match m WHERE m.tournament.id = :tournamentId", Match.class);
            query.setParameter("tournamentId", tournamentId);
            return query.getResultList();
        } catch (HibernateException he) {
            throw new RuntimeException(he);
        }
    }
}
