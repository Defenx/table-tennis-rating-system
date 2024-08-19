package listeners;

import config.AppConfig;
import jakarta.servlet.annotation.WebListener;
import services.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

        // Создание и настройка необходимых сервисов
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        LoginService loginService = new UserLoginService(bCryptPasswordEncoder);
        UserAuthenticationService userAuthenticationService = new UserAuthManager(loginService);

        // Настройка извлечения учетных данных
        CredentialsExtractor credentialsExtractor = new BasicCredentialsExtractor();

        // Сохранение атрибутов в контексте сервлета
        servletContext.setAttribute(AppConfig.getCredentialsExtractorAttribute(), credentialsExtractor);
        servletContext.setAttribute(AppConfig.getUserAuthServiceAttribute(), userAuthenticationService);
    }
}
