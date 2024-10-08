package servlet.home;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import listener.ContextListener;
import service.tournament.TournamentService;
import service.home.TournamentAttributeResolver;

public class BaseHomeServlet extends HttpServlet {
    protected TournamentAttributeResolver tournamentAttributeResolver;
    protected TournamentService tournamentService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var servletContext = config.getServletContext();
        tournamentAttributeResolver = (TournamentAttributeResolver) servletContext.getAttribute(ContextListener.TOURNAMENT_ATTRIBUTE_RESOLVER);
        tournamentService = (TournamentService) servletContext.getAttribute(ContextListener.TOURNAMENT_SERVICE);
    }
}
