package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.login.UserAuthenticationService;
import servlet.Route;

import java.io.IOException;
import java.util.Objects;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String requestURI = req.getRequestURI();
        if (Objects.equals(requestURI, Route.LOGIN) ||
                Objects.equals(requestURI, Route.REGISTRATION) ||
                req.getSession().getAttribute(UserAuthenticationService.getUSER_DTO_SESSION_ATTRIBUTE()) != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect(Route.LOGIN);
        }

    }
}
