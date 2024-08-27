package servlet;

import enums.ExtensionName;
import enums.TournamentType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(Route.TOURNAMENT_CREATE)
public class TournamentCreateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("defaultTournamentType", TournamentType.SINGLE_PLAYER);
        req.setAttribute("tournamentTypes", TournamentType.values());
        req.setAttribute("extensions", ExtensionName.values());

        req.getRequestDispatcher(Route.TOURNAMENT_CREATE_JSP).forward(req, resp);
    }
}