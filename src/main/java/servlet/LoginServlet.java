package servlet;

import Services.LoginService;
import dto.UserDto;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    LoginService loginService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        loginService = (LoginService) config.getServletContext().getAttribute("loginService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = req.getParameter("email");
        String password = req.getParameter("password");
        Optional<UserDto> existedUser = loginService.getExistedUser(username, password);
        if (existedUser.isPresent()) {
            session.setAttribute("userID", existedUser.get().getId());
            session.setAttribute("first_name", existedUser.get().getFirstName());
            session.setAttribute("last_name", existedUser.get().getLastName());
            session.setAttribute("rating", existedUser.get().getRating());
            session.setAttribute("user_role", existedUser.get().getRole());
            resp.sendRedirect("/");
        } else {
            resp.sendRedirect("/login");
        }
    }
}
