package servlet.home;

import constant.RouteConstants;
import constant.SessionAttributes;
import entity.User;
import enums.Route;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(RouteConstants.HOME)
public class HomeServlet extends BaseHomeServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE);

        var tournaments = tournamentService.getNewTournaments();
        Map<String, Object> reqAttributes = tournamentAttributeResolver.findNeededAttributes(user, tournaments);

        reqAttributes.forEach(req::setAttribute);
        req.setAttribute("tournaments", tournaments);

        req.getRequestDispatcher(Route.HOME.getJspPath()).forward(req, resp);
    }
}
