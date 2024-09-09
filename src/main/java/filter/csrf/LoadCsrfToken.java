package filter.csrf;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import constant.SessionAttributes;
import filter.BaseFilter;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@SuppressWarnings(value = "unchecked")
public class LoadCsrfToken extends BaseFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();

        Cache<String, Boolean> csrfSaltCache = (Cache<String, Boolean>) session.getAttribute(SessionAttributes.CSRF_TOKEN_CACHE_ATTRIBUTE);

        if (csrfSaltCache == null) {
            csrfSaltCache = CacheBuilder.newBuilder()
                    .maximumSize(5000)
                    .expireAfterWrite(4, TimeUnit.HOURS).build();

            session.setAttribute(SessionAttributes.CSRF_TOKEN_CACHE_ATTRIBUTE, csrfSaltCache);
        }

        String csrfToken = RandomStringUtils.random(20, 0, 0, true, true, null, new SecureRandom());

        csrfSaltCache.put(csrfToken, Boolean.TRUE);

        request.setAttribute(SessionAttributes.CSRF_TOKEN_ATTRIBUTE, csrfToken);

        chain.doFilter(request, response);
    }
}
