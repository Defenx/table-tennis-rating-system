package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import listener.ContextListener;
import service.validation.ValidationService;
import servlet.Route;

import java.io.IOException;

@WebFilter("/*")
public class ValidationFilter implements Filter {
    private ValidationService validationService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        validationService = (ValidationService) filterConfig.getServletContext().getAttribute(ContextListener.VALIDATION_SERVICE);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;

        if (!httpRequest.getMethod().equalsIgnoreCase("POST")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        var validatedHttpRequest = validationService.validate(httpRequest);
        var isValidRequest = (boolean) validatedHttpRequest.getAttribute(ValidationService.IS_VALID_REQUEST);

        if (isValidRequest) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            validatedHttpRequest.getRequestDispatcher(Route.TEST_REGISTRATION_PAGE.getJspPath()).forward(validatedHttpRequest, servletResponse);
        }
    }
}
