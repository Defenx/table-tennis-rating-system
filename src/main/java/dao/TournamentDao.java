package dao;

import entity.Extension;
import entity.Tournament;
import enums.Status;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class TournamentDao {
    private final SessionFactory sessionFactory;

    public Tournament getTournamentById(UUID tournamentId) {
        Transaction transaction = null;
        Tournament tournament;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<Tournament> query = session.createQuery("FROM Tournament t WHERE t.id = :tournamentId", Tournament.class);
            query.setParameter("tournamentId", tournamentId);
            tournament = query.uniqueResult();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        return tournament;
    }

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
    public List<Tournament> findTournamentsWithTheDesiredStatus(Status status) {
        try (Session session = sessionFactory.openSession()) {
            Query<Tournament> query = session.createQuery("FROM Tournament t WHERE t.status = :status", Tournament.class);
            query.setParameter("status", status);
            return query.getResultList();
        } catch (HibernateException he) {
            throw new RuntimeException(he);
        }
    }
    public List<Object[]> findTournamentsAndCountParticipants(Status status) {
        try (Session session = sessionFactory.openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT t.id, t.date, t.type, COUNT(tp.id) FROM Tournament t left JOIN TournamentParticipant tp ON t.id = tp.tournament.id WHERE t.status = :status  GROUP BY t.id, t.date,t.type",
                                                              Object[].class
            );
            query.setParameter("status", status);
            return query.getResultList();
        } catch (HibernateException he) {
            throw new RuntimeException(he);
        }
    }

    public void deleteTournament(Tournament tournament) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(tournament);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
