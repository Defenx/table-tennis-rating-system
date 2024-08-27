package servlet;

import enums.ExtensionName;
import enums.TournamentType;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import listener.ContextListener;
import service.tournament.create.TournamentCreateExtractorService;
import service.tournament.create.TournamentCreateService;

import java.io.IOException;

@WebServlet(Route.TOURNAMENT_CREATE)
public class TournamentCreateServlet extends HttpServlet {
    private TournamentCreateService tournamentCreateService;
    private TournamentCreateExtractorService tournamentCreateExtractorService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        tournamentCreateService = (TournamentCreateService) servletContext.getAttribute(ContextListener.TOURNAMENT_CREATE_SERVICE);
        tournamentCreateExtractorService = (TournamentCreateExtractorService) servletContext.getAttribute(ContextListener.TOURNAMENT_CREATE_EXTRACTOR_SERVICE);
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("tournamentTypes", TournamentType.values());
        req.setAttribute("extensionNames", ExtensionName.values());
        req.getRequestDispatcher(Route.TOURNAMENT_CREATE_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var extract = tournamentCreateExtractorService.extract(req);
        tournamentCreateService.addTournament(extract);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}