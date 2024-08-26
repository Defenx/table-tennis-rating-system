package filter;

import dto.UserDto;
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

        var user = (UserDto) request.getServletContext().getAttribute("User");
        var role = user.role();

        if (role != null && role.equals(Role.ADMIN)) {
            chain.doFilter(request, response);
        }
        else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/");
        }
    }
}
