package filter;

import entity.User;
import enums.Role;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.Route;

import java.io.IOException;

@WebFilter(Route.ADMIN_TOURNAMENT_CREATE)
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        var httpRequest = (HttpServletRequest) request;
        var httpResponse = (HttpServletResponse) response;

        var user = (User) httpRequest.getSession().getAttribute("user");
        if (user != null && Role.ADMIN.equals(user.getRole())) {
            chain.doFilter(request, response);
            return;
        }
        httpResponse.sendRedirect(httpRequest.getContextPath() + "/");
    }
}
