package filter.csrf;

import com.google.common.cache.Cache;
import constant.SessionAttributes;
import filter.BaseFilter;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@SuppressWarnings(value = "unchecked")
public class ValidateSalt extends BaseFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String csrfToken = request.getParameter(SessionAttributes.CSRF_TOKEN_ATTRIBUTE);

        System.out.println(csrfToken);

        Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>) request
                .getSession()
                .getAttribute(SessionAttributes.CSRF_TOKEN_CACHE_ATTRIBUTE);

        System.out.println(csrfPreventionSaltCache.asMap());

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
