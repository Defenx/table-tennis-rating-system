package servlet.home;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import listener.ContextListener;
import service.home.TournamentHelperService;

public class BaseHomeServlet extends HttpServlet {
    protected TournamentHelperService tournamentHelperService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var servletContext = config.getServletContext();
        tournamentHelperService = (TournamentHelperService) servletContext.getAttribute(ContextListener.TOURNAMENT_HELPER_SERVICE);
    }
}
