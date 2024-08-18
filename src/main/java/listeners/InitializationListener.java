package listeners;

import Services.LoginService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class InitializationListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        LoginService loginService = new LoginService(bCryptPasswordEncoder);
        servletContext.setAttribute("loginService", loginService);
    }

}
