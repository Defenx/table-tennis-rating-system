package servlet.home;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.Route;

import java.io.IOException;

@WebServlet(Route.DELETE_TOURNAMENT)
public class HomeServletDeleteTournament extends BaseHomeServlet{

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var optionalTournament = tournamentHelperService.getNewTournament();
        if (optionalTournament.isPresent()) {
            tournamentHelperService.deleteTournament(optionalTournament.get());
            resp.sendRedirect(Route.HOME);
        }
    }
}
