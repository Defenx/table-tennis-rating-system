package listeners;

import config.AppConfig;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import listeners.factories.DefaultServiceFactory;
import listeners.factories.ServiceFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import services.CredentialsExtractor;
import services.LoginService;
import services.UserAuthenticationService;

/**
 * Класс слушателя инициализации приложения.
 * Используется для настройки и инициализации необходимых сервисов при старте веб-приложения.
 */
@WebListener
public class InitializationListener implements ServletContextListener {

    /**
     * Метод вызывается при инициализации контекста сервлета.
     *
     * @param sce событие инициализации контекста сервлета
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        try {
            // Загрузка конфигурации приложения
            AppConfig.loadConfig();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось инициализировать приложение", e);
        }

        // Создание фабрики сервисов
        ServiceFactory serviceFactory = new DefaultServiceFactory();

        // Создание и настройка необходимых сервисов
        BCryptPasswordEncoder bCryptPasswordEncoder = serviceFactory.createPasswordEncoder();
        LoginService loginService = serviceFactory.createLoginService(bCryptPasswordEncoder);
        UserAuthenticationService userAuthenticationService = serviceFactory.createUserAuthenticationService(loginService);
        CredentialsExtractor credentialsExtractor = serviceFactory.createCredentialsExtractor();

        // Сохранение атрибутов в контексте сервлета
        servletContext.setAttribute(AppConfig.getCredentialsExtractorAttribute(), credentialsExtractor);
        servletContext.setAttribute(AppConfig.getUserAuthServiceAttribute(), userAuthenticationService);
    }
}