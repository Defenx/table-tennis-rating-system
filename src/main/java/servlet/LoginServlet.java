package servlet;

import constant.RouteConstants;
import constant.SessionAttributes;
import enums.Route;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import listener.ContextListener;
import service.UserService;
import service.login.CredentialsExtractor;

import java.io.IOException;

@WebServlet(RouteConstants.LOGIN)
public class LoginServlet extends HttpServlet {
    private UserService userService;
    private CredentialsExtractor credentialsExtractor;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) config.getServletContext().getAttribute(ContextListener.USER_SERVICE);
        credentialsExtractor = (CredentialsExtractor) config.getServletContext().getAttribute(ContextListener.CREDENTIALS_EXTRACTOR);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Route.LOGIN.getJspPath()).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var credentials = credentialsExtractor.extract(req);
        var existedUser = userService.getExistedUser(credentials.username(), credentials.password());

        if (existedUser.isPresent()) {
            req.getSession().setAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE, existedUser.get());
            resp.sendRedirect(RouteConstants.HOME);
        } else {
            req.setAttribute("loginError", "Неверный логин и/или пароль");
            req.getRequestDispatcher(Route.LOGIN.getJspPath()).forward(req, resp);
        }
    }

}
