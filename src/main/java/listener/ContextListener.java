package listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import listener.ObjectCreator.ContextObjectCreator;
import listener.ObjectCreator.part.Authentication;
import listener.ObjectCreator.part.ConfigProperties;
import listener.ObjectCreator.part.DataBase;
import lombok.SneakyThrows;
import org.hibernate.SessionFactory;


@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    @SneakyThrows
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        ContextObjectCreator objectCreator = new ContextObjectCreator();

        objectCreator.addContextObjects(new DataBase());
        objectCreator.addContextObjects(new ConfigProperties());
        objectCreator.addContextObjects(new Authentication());

        objectCreator.getServices().forEach(servletContext::setAttribute);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        SessionFactory sessionFactory = (SessionFactory) servletContextEvent.getServletContext().getAttribute("sessionFactory");
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}