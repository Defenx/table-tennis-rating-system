package filter;

import enums.FormMethod;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

@WebFilter("/*")
public class MethodFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String method = req.getParameter("_method");
        if (method != null) {
            if (Arrays.stream(FormMethod.values()).map(Enum::toString).toList().contains(method)) {
                HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(req) {
                    @Override
                    public String getMethod() {
                        return method;
                    }
                };
                filterChain.doFilter(requestWrapper, resp);
            }
        } else
            filterChain.doFilter(req, resp);
    }
}
