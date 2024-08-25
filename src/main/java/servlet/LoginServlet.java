package servlet;

import dto.Credentials;
import entity.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import listener.ContextListener;
import service.login.CredentialsExtractor;
import service.login.UserAuthenticationService;

import java.io.IOException;
import java.util.Optional;

@WebServlet(Route.LOGIN)
public class LoginServlet extends HttpServlet {
    private UserAuthenticationService userAuthenticationService;
    private CredentialsExtractor credentialsExtractor;

    private static final String AUTHENTICATION_FAILED_MESSAGE =  "authenticationFailed";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userAuthenticationService = (UserAuthenticationService) config.getServletContext().getAttribute(ContextListener.USER_AUTH_SERVICE);
        credentialsExtractor = (CredentialsExtractor) config.getServletContext().getAttribute(ContextListener.CREDENTIALS_EXTRACTOR);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Route.LOGIN_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Credentials credentials = credentialsExtractor.extract(req);

            if (credentials.username() == null
                    || credentials.password() == null
                    || credentials.username().isEmpty()
                    || credentials.password().isEmpty()) {
                userAuthenticationService.handleAuthenticationFailure(req, resp);
                return;
            }

            Optional<User> existedUser = userAuthenticationService.authenticate(credentials.username(), credentials.password());
            if (existedUser.isPresent()) {
                userAuthenticationService.setSessionAttributes(req, resp, existedUser.get());
            } else {
                userAuthenticationService.handleAuthenticationFailure(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute(ContextListener.USER_AUTH_SERVICE, AUTHENTICATION_FAILED_MESSAGE);
            req.getRequestDispatcher(Route.LOGIN_JSP).forward(req, resp);
        }
    }
}
