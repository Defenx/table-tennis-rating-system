package servlet;

import entity.Extension;
import entity.Tournament;
import enums.ExtensionName;
import enums.Status;
import enums.TournamentType;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import listener.ContextListener;
import service.TournamentService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet(Route.TOURNAMENT_CREATE)
public class TournamentCreateServlet extends HttpServlet {
    private TournamentService tournamentService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        tournamentService = (TournamentService) config.getServletContext().getAttribute(ContextListener.TOURNAMENT_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("defaultTournamentType", TournamentType.SINGLE_PLAYER);
        req.setAttribute("tournamentTypes", TournamentType.values());
        req.setAttribute("extensions", ExtensionName.values());

        req.getRequestDispatcher(Route.TOURNAMENT_CREATE_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        // get attributes from the form
        // create object tournament
        // object save in db

        var tournamentExtensions = new ArrayList<Extension>();

        for (var extensionName : ExtensionName.values()) {
            var extensionValue = req.getParameter(String.valueOf(extensionName));
            if (extensionValue == null) {
                continue;
            }
            var extension = Extension.builder().name(extensionName).value(extensionValue).build();
            tournamentExtensions.add(extension);
        }

        var tournamentTypeStr = req.getParameter("type");
        var tournamentType = TournamentType.valueOf(tournamentTypeStr);

        var tournamentDateStr = req.getParameter("date");
        var tournamentDate = LocalDate.parse(tournamentDateStr);

        var tournament = Tournament.builder()
                .type(tournamentType)
                .date(tournamentDate)
                .extensions(tournamentExtensions)
                .stage(0)
                .status(Status.NEW)
                .build();

        tournamentService.add(tournament);
    }
}