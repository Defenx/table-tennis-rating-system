package servlet.home;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.Route;

import java.io.IOException;

import static servlet.Route.HOME;

@WebServlet(Route.DELETE_TOURNAMENT)
public class HomeServletDeleteTournament extends BaseHomeServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        tournamentService.deleteTournament();

        resp.sendRedirect(HOME);
    }
}
