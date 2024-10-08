package dao;

import entity.Tournament;
import entity.TournamentParticipant;
import entity.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;

import java.util.UUID;

@RequiredArgsConstructor
public class TournamentParticipantDao {

    private final SessionFactory sessionFactory;

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
}
