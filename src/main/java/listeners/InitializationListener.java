package listeners;

import config.AppConfig;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import listeners.factories.DefaultServiceFactory;
import listeners.factories.ServiceFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import services.login.CredentialsExtractor;
import services.login.LoginService;
import services.login.auth.JwtTokenService;
import services.login.auth.strategies.AuthenticationStrategy;

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
        // Загрузка конфигурации приложения
        try {

            AppConfig.loadConfig();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось инициализировать приложение", e);
        }

        // Создание фабрики сервисов
        ServiceFactory serviceFactory = new DefaultServiceFactory();

        // Создание и настройка необходимых сервисов
        BCryptPasswordEncoder bCryptPasswordEncoder = serviceFactory.createPasswordEncoder();
        LoginService loginService = serviceFactory.createLoginService(bCryptPasswordEncoder);
        JwtTokenService jwtTokenService = serviceFactory.createJwtTokenService();
        String authStrategyType = "JWT";

        AuthenticationStrategy authenticationStrategy = serviceFactory
                .createUserAuthenticationService(loginService, jwtTokenService, authStrategyType);
        CredentialsExtractor credentialsExtractor = serviceFactory
                .createCredentialsExtractor();

        // Сохранение атрибутов в контексте сервлета
        servletContext.setAttribute(AppConfig
                .getCredentialsExtractorAttribute(), credentialsExtractor);
        servletContext.setAttribute(AppConfig
                .getUserAuthServiceAttribute(), authenticationStrategy);
        servletContext.setAttribute(AppConfig
                .getJwtTokenServiceAttribute(), jwtTokenService);
    }
}