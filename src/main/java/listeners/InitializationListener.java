package listeners;


import config.ConstantsConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import services.UserService;

/**
 * The InitializationListener class initializes necessary components when the web application starts.
 */

@WebListener
public class InitializationListener implements ServletContextListener {
    /**
     * This method is called when the servlet context is initialized.
     * It creates and configures the ConstantsConfig and UserService objects,
     * and then stores them in the servlet context attributes.
     *
     * @param sce the servlet context event
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        ConstantsConfig constantsConfig = new ConstantsConfig();
        UserService userService = new UserService();

        servletContext.setAttribute("constantsConfig", constantsConfig);
        servletContext.setAttribute("userService", userService);
    }
}
