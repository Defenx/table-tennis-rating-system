package listener.factory;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import service.Service;
import jakarta.servlet.ServletContext;
import service.UserService;
import service.login.BasicCredentialsExtractorService;
import service.login.auth.JwtTokenService;
import service.login.auth.UserAuthenticationService;
import service.login.auth.strategies.AuthenticationStrategy;
import service.login.auth.strategies.JwtAuthenticationStrategy;
import java.util.HashMap;
import java.util.Map;

public class ServiceFactory {
    private final Map<String, Service> services = new HashMap<>();

    public void registerService(String name, Service service, ServletContext servletContext) {
        services.put(name, service);
        servletContext.setAttribute(name, service);
    }

    public static ServiceFactory createDefault(ServletContext servletContext) {
        ServiceFactory factory = new ServiceFactory();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        UserService userService = new UserService(bCryptPasswordEncoder);
        JwtTokenService jwtTokenService = new JwtTokenService();
        AuthenticationStrategy authenticationStrategy = new JwtAuthenticationStrategy(userService, jwtTokenService);
        Service userAuthenticationService = new UserAuthenticationService(authenticationStrategy);
        Service credentialsExtractor = new BasicCredentialsExtractorService();

        factory.registerService("userService", userService, servletContext);
        factory.registerService("jwtTokenService", jwtTokenService, servletContext);
        factory.registerService("userAuthenticationService", userAuthenticationService, servletContext);
        factory.registerService("credentialsExtractor", credentialsExtractor, servletContext);

        return factory;
    }
}