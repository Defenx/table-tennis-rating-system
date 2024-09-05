package servlet;

import constant.RouteConstants;
import dto.RegistrationFormDto;
import enums.Route;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import listener.ContextListener;
import service.UserService;
import service.registration.RegistrationDataExtractor;
import servlet.util.TooltipUtil;

import java.io.IOException;

@WebServlet(RouteConstants.REGISTRATION)
public class RegistrationServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        userService = (UserService) servletContext.getAttribute(ContextListener.USER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TooltipUtil.setTooltips(req);
        req.getRequestDispatcher(Route.REGISTRATION.getJspPath()).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegistrationFormDto userData = RegistrationDataExtractor.extract(req);
        try {
            userService.addUser(userData);
            resp.sendRedirect(RouteConstants.LOGIN);
        } catch (Exception e) {
            req.getRequestDispatcher(RouteConstants.REGISTRATION).forward(req, resp);
        }
    }
}
