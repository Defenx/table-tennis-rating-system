package servlet;

import constant.RouteConstants;
import enums.ExtensionName;
import enums.Route;
import enums.TournamentType;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import listener.ContextListener;
import service.tournament.create.TournamentCreateExtractorService;
import service.tournament.create.TournamentCreateService;

import java.io.IOException;

@WebServlet(RouteConstants.ADMIN_TOURNAMENT_CREATE)
public class TournamentCreateServlet extends HttpServlet {
    private TournamentCreateService tournamentCreateService;
    private TournamentCreateExtractorService tournamentCreateExtractorService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        tournamentCreateService = (TournamentCreateService) config.getServletContext().getAttribute(ContextListener.TOURNAMENT_CREATE_SERVICE);
        tournamentCreateExtractorService = (TournamentCreateExtractorService) config.getServletContext().getAttribute(ContextListener.TOURNAMENT_CREATE_EXTRACTOR_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Route.ADMIN_TOURNAMENT_CREATE.getJspPath()).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var extractTournamentDto = tournamentCreateExtractorService.extract(req);
        tournamentCreateService.createTournament(extractTournamentDto);
        resp.sendRedirect(RouteConstants.HOME);
    }
}