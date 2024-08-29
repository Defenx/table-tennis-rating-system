package servlet.home;

import constant.RequestAttributes;
import constant.RouteConstants;
import constant.SessionAttributes;
import entity.Tournament;
import entity.User;
import enums.Route;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.home.TournamentServiceMethod;


import java.io.IOException;

@WebServlet(RouteConstants.HOME)
public class HomeServlet extends BaseHomeServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE);

        var reqAttributes = tournamentAttributeResolver.findNeededAttributes(user);
        reqAttributes.forEach(req::setAttribute);

        req.getRequestDispatcher(Route.HOME.getJspPath()).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getAndApplyAttributesToMethod(req, tournamentService::participate);

        resp.sendRedirect(Route.HOME.getPath());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getAndApplyAttributesToMethod(req, tournamentService::withdrawFromTheTournament);

        resp.sendRedirect(Route.HOME.getPath());
    }

    private void getAndApplyAttributesToMethod(HttpServletRequest req, TournamentServiceMethod method) {
        User user = (User) req.getSession().getAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE);
        var reqAttributes = tournamentAttributeResolver.findNeededAttributes(user);
        reqAttributes.forEach(req::setAttribute);
        Tournament tournament = (Tournament) req.getAttribute(RequestAttributes.TOURNAMENT_REQUEST_ATTRIBUTE);

        method.apply(user, tournament);
    }
}

