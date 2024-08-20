package listeners;


import config.ConstantsConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import services.UserService;

@WebListener
public class InitializationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        ConstantsConfig constantsConfig = new ConstantsConfig();
        UserService userService = new UserService();

        servletContext.setAttribute("constantsConfig", constantsConfig);
        servletContext.setAttribute("userService", userService);
    }
}
