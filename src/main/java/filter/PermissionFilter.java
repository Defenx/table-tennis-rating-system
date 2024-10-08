package filter;

import constant.RouteConstants;
import constant.SessionAttributes;
import entity.User;
import enums.Route;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public class PermissionFilter extends BaseFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        var routeOptional = Route.fromPath(request.getServletPath());

        if (routeOptional.isPresent()) {
            var route = routeOptional.get();
            var optionalUser = Optional.ofNullable((User) request
                    .getSession()
                    .getAttribute(SessionAttributes.USER_SESSION_ATTRIBUTE));

            if (optionalUser.isPresent() && route.isAllowedForRole(optionalUser.get().getRole())) {
                chain.doFilter(request, response);
                return;
            } else if (optionalUser.isPresent()) {
                response.sendRedirect(RouteConstants.HOME);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
