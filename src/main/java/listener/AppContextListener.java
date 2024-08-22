package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import service.validation.ValidationService;
import service.validation.ValidationRegistry;

@WebListener
public class AppContextListener implements ServletContextListener {
    public static final String VALIDATION_SERVICE = "validationService";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        var servletContext = sce.getServletContext();
        var validationFactory = new ValidationRegistry();
        var validationService = new ValidationService(validationFactory);

        servletContext.setAttribute(VALIDATION_SERVICE, validationService);
    }
}
