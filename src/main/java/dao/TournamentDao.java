package dao;

import entity.Extension;
import entity.Tournament;
import lombok.RequiredArgsConstructor;
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
        try (Session session = sessionFactory.openSession()) {
            return session.get(Tournament.class, tournamentId);
        }
    }

    public void create(Tournament tournament) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            for (Extension extension : tournament.getExtensions()) {
                extension.setTournament(tournament);
            }
            session.persist(tournament);
            transaction.commit();
        }
    }

    public List<Tournament> findTournamentsWhereStatusIsNew() {
        try (Session session = sessionFactory.openSession()) {
            Query<Tournament> query = session.createQuery("FROM Tournament t WHERE t.status = 'NEW'", Tournament.class);
            return query.getResultList();
        }
    }

    public void deleteTournament(Tournament tournament) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(tournament);
            transaction.commit();
        }
    }
}
