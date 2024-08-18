package listeners;

import config.AppConfig;
import jakarta.servlet.annotation.WebListener;
import services.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@WebListener
public class InitializationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        try {
            AppConfig.loadConfig();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize application", e);
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        LoginService loginService = new UserLoginService(bCryptPasswordEncoder);
        CredentialsExtractor credentialsExtractor = new BasicCredentialsExtractor();

        UserAuthenticationService userAuthenticationService = new UserAuthManager(loginService);

        servletContext.setAttribute(AppConfig.getCredentialsExtractorAttribute(), credentialsExtractor);
        servletContext.setAttribute(AppConfig.getUserAuthServiceAttribute(), userAuthenticationService);
    }


}