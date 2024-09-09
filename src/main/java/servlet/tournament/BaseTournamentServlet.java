package servlet.tournament;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import listener.ContextListener;
import service.TournamentService;

public class BaseTournamentServlet extends HttpServlet {
    protected TournamentService tournamentService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var servletContext = config.getServletContext();
        tournamentService = (TournamentService) servletContext.getAttribute(ContextListener.TOURNAMENT_SERVICE);
    }
}
