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
        String salt = request.getParameter(SessionAttributes.SALT_ATTRIBUTE);

        Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>) request
                .getSession()
                .getAttribute(SessionAttributes.CSRF_SALT_CACHE_ATTRIBUTE);

        if (
                csrfPreventionSaltCache != null
                        && salt != null
                        && csrfPreventionSaltCache.getIfPresent(salt) != null
        ) {
            chain.doFilter(request, response);
        } else {
            throw new IllegalStateException("CSRF error!");
        }
    }
}
