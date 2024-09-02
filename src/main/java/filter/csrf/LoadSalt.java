package filter.csrf;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import constant.SessionAttributes;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;
public class LoadSalt implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();
        Cache<String,Boolean> csrfSaltCache =(Cache<String,Boolean>) session.getServletContext().getAttribute(SessionAttributes.CSRF_SALT_CACHE_ATTRIBUTE);
        if(csrfSaltCache==null){
            csrfSaltCache = CacheBuilder.newBuilder()
                    .maximumSize(5000)
                    .expireAfterWrite(30, TimeUnit.MINUTES).build();
            session.setAttribute(SessionAttributes.CSRF_SALT_CACHE_ATTRIBUTE, csrfSaltCache);
        }
        String salt = RandomStringUtils.random(20,0,0,true,true,null,new SecureRandom());
        csrfSaltCache.put(salt,true);
        req.setAttribute(SessionAttributes.SALT_ATTRIBUTE,salt);
        filterChain.doFilter(req,servletResponse);
    }
}
