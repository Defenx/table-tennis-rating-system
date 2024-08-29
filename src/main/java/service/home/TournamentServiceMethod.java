package service.home;

import entity.Tournament;
import entity.User;

@FunctionalInterface
public interface TournamentServiceMethod {
    void apply(User user, Tournament tournament);
}
