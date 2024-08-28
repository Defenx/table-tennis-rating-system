package filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * The type Base filter.
 */
public abstract class BaseFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        doFilterInternal(httpRequest, httpResponse, chain);
    }

    /**
     * Do filter internal.
     *
     * @param request  the request
     * @param response the response
     * @param chain    the chain
     * @throws IOException      the io exception
     * @throws ServletException the servlet exception
     */
    protected abstract void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException;
}
