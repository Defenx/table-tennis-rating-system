package servlet;

import enums.ExtensionName;
import enums.TournamentType;
import jakarta.servlet.ServletConfig;
<<<<<<< HEAD
import jakarta.servlet.ServletContext;
=======
>>>>>>> origin/tournament/create+Lev
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import listener.ContextListener;
<<<<<<< HEAD
import service.tournament.create.TournamentCreateExtractorService;
import service.tournament.create.TournamentCreateService;
=======
import service.TournamentDataExtractor;
import service.TournamentService;
>>>>>>> origin/tournament/create+Lev

import java.io.IOException;

@WebServlet(Route.TOURNAMENT_CREATE)
public class TournamentCreateServlet extends HttpServlet {
<<<<<<< HEAD
    private TournamentCreateService tournamentCreateService;
    private TournamentCreateExtractorService tournamentCreateExtractorService;
=======
    private TournamentService tournamentService;
    private TournamentDataExtractor tournamentDataExtractor;
>>>>>>> origin/tournament/create+Lev

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        tournamentService = (TournamentService) config.getServletContext().getAttribute(ContextListener.TOURNAMENT_SERVICE);
        tournamentDataExtractor = (TournamentDataExtractor) config.getServletContext().getAttribute(ContextListener.TOURNAMENT_DATA_EXTRACTOR);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("defaultTournamentType", TournamentType.SINGLE_PLAYER);
        req.setAttribute("tournamentTypes", TournamentType.values());
        req.setAttribute("extensions", ExtensionName.values());
        req.getRequestDispatcher(Route.TOURNAMENT_CREATE_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var extract = tournamentCreateExtractorService.extract(req);
        tournamentCreateService.addTournament(extract);
        resp.sendRedirect(Route.TOURNAMENT_CREATE);    }
}