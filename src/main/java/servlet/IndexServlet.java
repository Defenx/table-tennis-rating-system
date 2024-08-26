package servlet;

import entity.Tournament;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import listener.ContextListener;
import service.TournamentService;
import service.home.TournamentHelperService;

import java.io.IOException;
@WebServlet(Route.HOME_PAGE)
public class IndexServlet extends HttpServlet {
    private TournamentHelperService tournamentHelperService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var servletContext = config.getServletContext();
        tournamentHelperService = (TournamentHelperService) servletContext.getAttribute(ContextListener.TOURNAMENT_HELPER_SERVICE);
    }
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var tournament = tournamentHelperService.getNewTournament();
        tournamentHelperService.setSessionAttributes(req,tournament);
        req.getRequestDispatcher(req.getContextPath() + Route.HOME_PAGE_JSP).forward(req, resp);
    }

}
