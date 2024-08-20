package servlets;

import config.ConstantsConfig;
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

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private UserService userService;
    private ConstantsConfig constantsConfig;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        userService = (UserService) servletContext.getAttribute("userService");
        constantsConfig = (ConstantsConfig) servletContext.getAttribute("constantsConfig");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(constantsConfig.getRegistrationJsp()).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegistrationDto userData = RegistrationDataExtractor.extract(req);
        boolean isAdded = userService.addUser(userData);

        if (isAdded) {
            resp.setStatus(201);
            resp.sendRedirect(constantsConfig.getLoginURL());
        } else {
            resp.setStatus(500);
            req.setAttribute("message", constantsConfig.getErrorUserAddMessage());
            req.getRequestDispatcher(constantsConfig.getErrorJsp()).forward(req, resp);
        }
    }
}
