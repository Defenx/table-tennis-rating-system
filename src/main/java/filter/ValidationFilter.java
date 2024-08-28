package filter;

import enums.Route;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import listener.ContextListener;
import service.validation.ValidationService;

import java.io.IOException;

/**
 * The type Validation filter.
 */
public class ValidationFilter extends BaseFilter {
    private ValidationService validationService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        validationService = (ValidationService) filterConfig
                .getServletContext()
                .getAttribute(ContextListener.VALIDATION_SERVICE);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        var validatedHttpRequest = validationService.validate(request);
        var isValidRequest = (boolean) validatedHttpRequest.getAttribute(ValidationService.IS_VALID_REQUEST);

        if (isValidRequest) {
            chain.doFilter(request, response);
        } else {
            var requestedEndpoint = request.getRequestURI();
            var optionalRoute = Route.fromPath(requestedEndpoint);
            if (optionalRoute.isPresent()) {
                var errorPageJsp = optionalRoute.get().getJspPath();
                validatedHttpRequest.getRequestDispatcher(errorPageJsp).forward(validatedHttpRequest, response);
            }
        }
    }
}
