package filter.csrf;

import com.google.common.cache.Cache;
import constant.RouteConstants;
import constant.SessionAttributes;
import enums.FormMethod;
import filter.BaseFilter;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@SuppressWarnings(value = "unchecked")
public class ValidateCsrfToken extends BaseFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(
                        request.getMethod().equals(FormMethod.GET.name())
                        || request.getServletPath().equals(RouteConstants.LOGIN)
        ){
            chain.doFilter(request, response);
            return;
        }

        String csrfToken = request.getParameter(SessionAttributes.CSRF_TOKEN_ATTRIBUTE);

        Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>) request
                .getSession()
                .getAttribute(SessionAttributes.CSRF_TOKEN_CACHE_ATTRIBUTE);
        if (
                        csrfPreventionSaltCache != null
                        && csrfToken != null
                        && csrfPreventionSaltCache.getIfPresent(csrfToken) != null
        ) {
            chain.doFilter(request, response);
        } else {
            throw new IllegalStateException("CSRF error!");
        }
    }
}
