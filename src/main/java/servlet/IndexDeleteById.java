package servlet;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import listener.ContextListener;
import service.home.TournamentHelperService;

import java.io.IOException;
import java.util.UUID;

@WebServlet(Route.DELETE_BY_PARTICIPANT_ID)
public class IndexDeleteById extends HttpServlet {
    private TournamentHelperService tournamentHelperService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var servletContext = config.getServletContext();
        tournamentHelperService = (TournamentHelperService) servletContext.getAttribute(ContextListener.TOURNAMENT_HELPER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        System.out.println(pathInfo);
        UUID userId = UUID.fromString(pathInfo.substring(1));
        tournamentHelperService.removeFromTournament(userId);
        resp.sendRedirect(Route.HOME_PAGE);
    }
}
