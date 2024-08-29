package servlet.home;

import constant.RouteConstants;
import enums.Route;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(RouteConstants.DELETE_TOURNAMENT)
public class HomeServletDeleteTournament extends BaseHomeServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        tournamentService.deleteTournament();

        resp.sendRedirect(Route.HOME.getPath());
    }
}
