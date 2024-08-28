package filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.Route;

import java.io.IOException;

public class RootRedirectFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (httpRequest.getRequestURI().equals("/")) {
            httpResponse.sendRedirect(Route.HOME);
        } else {
            chain.doFilter(httpRequest, httpResponse);
        }
    }
}
