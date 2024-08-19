package servlets;

import constants.Constants;
import dto.RegistrationDto;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import services.RegistrationDataExtractor;
import services.UserService;

import java.io.IOException;

@WebServlet(Constants.REGISTRATION_URL)
public class RegistrationServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        userService = (UserService) servletContext.getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Constants.REGISTRATION_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegistrationDto userData = RegistrationDataExtractor.extract(req);
        boolean isAdded = userService.addUser(userData);

        if (isAdded) {
            resp.setStatus(201);
            resp.sendRedirect(Constants.LOGIN_URL);
        } else {
            resp.setStatus(500);
            req.setAttribute("message", Constants.ERROR_USER_ADD_MESSAGE);
            req.getRequestDispatcher(Constants.ERROR_JSP).forward(req, resp);
        }
    }
}
