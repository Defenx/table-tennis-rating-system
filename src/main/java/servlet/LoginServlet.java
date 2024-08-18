package servlet;

import config.AppConfig;
import dto.Credentials;
import dto.UserDto;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.CredentialsExtractor;
import services.UserAuthenticationService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserAuthenticationService userAuthenticationService;
    private CredentialsExtractor credentialsExtractor;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userAuthenticationService = (UserAuthenticationService) config.getServletContext().getAttribute(AppConfig.getUserAuthServiceAttribute());
        credentialsExtractor = (CredentialsExtractor) config.getServletContext().getAttribute(AppConfig.getCredentialsExtractorAttribute());
        if (userAuthenticationService == null || credentialsExtractor == null) {
            throw new ServletException(AppConfig.getServiceNotInitializedMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(AppConfig.getLoginPath()).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Credentials credentials = credentialsExtractor.extract(req);

            if (credentials.getUsername() == null || credentials.getPassword() == null || credentials.getUsername().isEmpty() || credentials.getPassword().isEmpty()) {
                userAuthenticationService.handleAuthenticationFailure(req, resp);
                return;
            }

            Optional<UserDto> existedUser = userAuthenticationService.authenticate(credentials.getUsername(), credentials.getPassword());
            if (existedUser.isPresent()) {
                userAuthenticationService.setSessionAttributes(req, resp, existedUser.get());
            } else {
                userAuthenticationService.handleAuthenticationFailure(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute(AppConfig.getUserAuthServiceAttribute(), AppConfig.getAuthenticationFailedMessage());
            req.getRequestDispatcher(AppConfig.getLoginPath()).forward(req, resp);
        }
    }
}