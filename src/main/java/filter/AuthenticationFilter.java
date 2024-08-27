package filter;

import constant.SessionAttributes;
import entity.User;
import enums.Route;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.login.UserAuthenticationService;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        var httpResponse = (HttpServletResponse) response;
        var path = httpRequest.getServletPath();
        var routeOptional = Optional.ofNullable(Route.fromPath(path));
        if (routeOptional.isPresent()) {
            var userOptional = Optional.ofNullable((User) httpRequest
                    .getSession()
                    .getAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE));
            if (!routeOptional.get().isRequiresAuth() || userOptional.isPresent()) {
                chain.doFilter(request, response);
                return;
            }
        }
        httpResponse.sendRedirect(httpRequest.getContextPath() + Route.LOGIN.getPath());
    }
}
