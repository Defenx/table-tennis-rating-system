package filter;

import constant.RouteConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RootRedirectFilter extends BaseFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getRequestURI().equals(RouteConstants.ROOT)) {
            response.sendRedirect(RouteConstants.HOME);
            return;
        }
        chain.doFilter(request, response);
    }
}
