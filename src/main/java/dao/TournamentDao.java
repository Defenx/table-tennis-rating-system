package dao;

import entity.Extension;
import entity.Tournament;
import entity.TournamentParticipant;
import entity.User;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import java.util.Optional;
import java.util.UUID;

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

    public Optional<Tournament> findNewTournament() {
        try (Session session = sessionFactory.openSession()) {
            Query<Tournament> query = session.createQuery("FROM Tournament t WHERE t.status = 'NEW'", Tournament.class);
            Query<Tournament> tournamentQuery = query.setMaxResults(1);
            System.out.println("DAO: " + tournamentQuery.getSingleResult());
            return Optional.of(query.getSingleResult());
        } catch (HibernateException he) {
            throw new RuntimeException(he);
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }

    public void participateUserToTournament(User user, Tournament tournament) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            TournamentParticipant participant = new TournamentParticipant();
            participant.setUser(user);
            participant.setTournament(tournament);
            tournament.getParticipants().add(participant);
            session.persist(participant);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void removeFromTournament(UUID participantId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            MutationQuery mutationQuery = session.createMutationQuery("DELETE FROM TournamentParticipant tp WHERE id = :id");
            mutationQuery.setParameter("id", participantId);
            mutationQuery.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
