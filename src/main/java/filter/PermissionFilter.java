package filter;

import constant.SessionAttributes;
import entity.User;
import enums.Role;
import enums.Route;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebFilter("/*")
public class PermissionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        var httpRequest = (HttpServletRequest) request;
        var httpResponse = (HttpServletResponse) response;

        var userOptional = Optional.ofNullable((User) httpRequest
                .getSession()
                .getAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE));
        var userRole = userOptional.map(User::getRole).orElse(Role.GUEST);

        var path = httpRequest.getServletPath();
        var routeOptional = Optional.ofNullable(Route.fromPath(path));

        if (routeOptional.isPresent() && routeOptional.get().isRequiresAuth()) {
            if (userRole != Role.GUEST) {
                if (!routeOptional.get().getAllowedRoles().contains(userRole)) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + Route.ERROR.getPath());
                    return;
                }
            }
            else {
                if (!path.equals(Route.LOGIN.getPath())
                        && !path.equals(Route.REGISTRATION.getPath())) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + Route.LOGIN.getPath());
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }
}