package servlet.home;

import constant.RouteConstants;
import enums.Route;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(RouteConstants.DELETE_BY_PARTICIPANT_ID)
public class HomeServletDeleteById extends BaseHomeServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        UUID userId = UUID.fromString(pathInfo.substring(1));

        tournamentService.removeFromTournament(userId);

        resp.sendRedirect(Route.HOME.getPath());
    }
}
