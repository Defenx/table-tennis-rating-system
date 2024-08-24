package servlet;

import dto.Credentials;
import dto.UserDto;
import entity.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.login.CredentialsExtractor;
import service.login.UserAuthenticationService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserAuthenticationService userAuthenticationService;
    private CredentialsExtractor credentialsExtractor;

    private static final String USER_AUTH_SERVICE_ATTRIBUTE = "userAuthService";
    private static final String CREDENTIALS_EXTRACTOR_ATTRIBUTE = "credentialsExtractor";
    private static final String SERVICE_NOT_INITIALIZED_MESSAGE = "serviceNotInitialized";
    private static final String AUTHENTICATION_FAILED_MESSAGE =  "authenticationFailed";
    private static final String LOGIN_PAGE =  "/login.jsp";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userAuthenticationService = (UserAuthenticationService) config.getServletContext().getAttribute(USER_AUTH_SERVICE_ATTRIBUTE);
        credentialsExtractor = (CredentialsExtractor) config.getServletContext().getAttribute(CREDENTIALS_EXTRACTOR_ATTRIBUTE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
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
            req.setAttribute(USER_AUTH_SERVICE_ATTRIBUTE, AUTHENTICATION_FAILED_MESSAGE);
            req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
        }
    }
}
