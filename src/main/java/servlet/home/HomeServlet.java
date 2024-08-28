package servlet.home;

import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servlet.Route;

import java.io.IOException;

@WebServlet(Route.HOME)
public class HomeServlet extends BaseHomeServlet {
    private static final String USER = "user";


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var optionalTournament = tournamentHelperService.getNewTournament();
        tournamentHelperService.setSessionAttributes(req, resp, optionalTournament.orElse(null));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var optionalTournament = tournamentHelperService.getNewTournament();
        if (optionalTournament.isPresent()) {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute(USER);
            tournamentHelperService.participate(user, optionalTournament.get());
            resp.sendRedirect(Route.HOME);
        }
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
