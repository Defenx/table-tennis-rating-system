package filter;

import constant.SessionAttributes;
import entity.User;
import enums.Role;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.Route;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

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
        var userRole = userOptional.map(User::getRole).orElse(null);

        var path = httpRequest.getServletPath();
        var allowedRoles = getAllowedRolesForPath(path);

        if (userRole != null && allowedRoles.contains(userRole)) {
            chain.doFilter(request, response);
        }
        else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/");
        }
    }

    private Set<Role> getAllowedRolesForPath(String path) {
        switch (path) {
            case Route.LOGIN:
                return Route.LOGIN_ROLES;
            case Route.REGISTRATION:
                return Route.REGISTRATION_ROLES;
            case Route.ERROR:
                return Route.ERROR_ROLES;
            case Route.ADMIN_TOURNAMENT_CREATE:
                return Route.ADMIN_TOURNAMENT_CREATE_ROLES;
            default:
                return Set.of();
        }
    }
}