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
import service.registration.RegistrationDataExtractor;
import service.UserService;

import java.io.IOException;

/**
 * The RegistrationServlet class handles user registration requests.
 */
@WebServlet(RouteConstants.REGISTRATION)
public class RegistrationServlet extends HttpServlet {
    private static final String MESSAGE_ERROR = "Error while user adding! ";
    private UserService userService;

    /**
     * Initializes the servlet from the servlet context.
     *
     * @param config the ServletConfig object containing the servlet's configuration
     * @throws ServletException if an error occurs during initialization
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        userService = (UserService) servletContext.getAttribute(ContextListener.USER_SERVICE);
    }

    /**
     * Handles GET requests by forwarding to the registration page.
     *
     * @param req  the HttpServletRequest object containing the client's request
     * @param resp the HttpServletResponse object containing the server's response
     * @throws ServletException if an error occurs while processing the request
     * @throws IOException      if an input-output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Route.REGISTRATION.getJspPath()).forward(req, resp);
    }

    /**
     * Handles POST requests for registering a new user.
     *
     * @param req  the HttpServletRequest object containing the client's request
     * @param resp the HttpServletResponse object containing the server's response
     * @throws ServletException if an error occurs while processing the request
     * @throws IOException      if an input-output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegistrationFormDto userData = RegistrationDataExtractor.extract(req);
        try {
            userService.addUser(userData);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.sendRedirect(RouteConstants.LOGIN);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.setAttribute("errorMessage", MESSAGE_ERROR + e.getMessage());
            req.getRequestDispatcher(Route.REGISTRATION.getJspPath()).forward(req, resp);
        }
    }
}
