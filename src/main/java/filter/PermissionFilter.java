package filter;

import constant.SessionAttributes;
import entity.User;
import enums.Route;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public class PermissionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        var httpResponse = (HttpServletResponse) response;
        var routeOptional = Route.fromPath(httpRequest.getServletPath());

        if (routeOptional.isPresent()) {
            var route = routeOptional.get();

            var optionalUser = Optional.ofNullable((User) httpRequest
                    .getSession()
                    .getAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE));
            if (optionalUser.isPresent() && route.isAllowedForRole(optionalUser.get().getRole())) {
                chain.doFilter(request, response);
                return;
            } else if(optionalUser.isPresent()) {
                httpResponse.sendRedirect(Route.HOME.getPath());
            }

        }
        chain.doFilter(request, response);
    }
}