package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = Route.REGISTRATION)
public class FakeRegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("POST " + Route.REGISTRATION);

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

        resp.sendRedirect(Route.SUCCESS_REGISTRATION);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("GET " + Route.REGISTRATION);
        req.getRequestDispatcher(Route.REGISTRATION_JSP).forward(req, resp);
    }
}
