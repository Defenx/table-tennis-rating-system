package servlet.home;

import entity.Tournament;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.home.TournamentServiceMethod;
import servlet.Route;

import java.io.IOException;

@WebServlet(Route.HOME)
public class HomeServlet extends BaseHomeServlet {
    private static final String USER = "user";


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);

        var reqAttributes = tournamentAttributeResolver.findNeededAttributes(user);
        reqAttributes.forEach(req::setAttribute);

        req.getRequestDispatcher(Route.HOME_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getAndApplyAttributesToMethod(req, tournamentService::participate);

        resp.sendRedirect(Route.HOME);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var optionalTournament = tournamentHelperService.getNewTournament();
        if (optionalTournament.isPresent()) {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute(USER);
            tournamentHelperService.withdrawFromTheTournament(user, optionalTournament.get());
            resp.sendRedirect(Route.HOME);
        }
    }
}
