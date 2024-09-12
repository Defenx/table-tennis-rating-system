package servlet;

import constant.RouteConstants;
import entity.User;
import enums.Route;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import listener.ContextListener;
import service.user.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet(RouteConstants.STATISTIC)
public class StatisticServlet extends HttpServlet {
    private UserService userService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) config.getServletContext().getAttribute(ContextListener.USER_SERVICE);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> ratingUserList = userService.getOrderByRatingAsc();
        req.setAttribute("ratingUserList", ratingUserList);
        req.getRequestDispatcher(Route.STATISTIC.getJspPath()).forward(req, resp);
    }
}
