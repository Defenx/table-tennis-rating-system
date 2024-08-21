package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/test-registration")
public class TestValidationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("POST /test-registration");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String age = req.getParameter("age");
        String password = req.getParameter("password");

        System.out.println("Fake saving of a new user");
        System.out.println("name: " + name);
        System.out.println("email: " + email);
        System.out.println("age: " + age);
        System.out.println("password: " + password);
        System.out.println("Save is completed!");

        resp.sendRedirect("/success-test-registration");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("GET /test-registration");
        req.getRequestDispatcher(Route.TEST_REGISTRATION_PAGE.getJspPath()).forward(req, resp);
    }
}
