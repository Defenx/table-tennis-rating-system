package listener.factory;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import service.Service;
import service.login.BasicCredentialsExtractorService;
import service.login.CredentialsExtractor;
import service.UserService;
import service.login.auth.JwtTokenService;
import service.login.auth.UserAuthenticationService;
import service.login.auth.strategies.AuthenticationStrategy;
import service.login.auth.strategies.JwtAuthenticationStrategy;
import service.login.auth.strategies.StandardAuthenticationStrategy;

public class ServiceRegistrar {

    public static void registerServices(ServiceFactory factory) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        UserService userService = new UserService(bCryptPasswordEncoder);
        JwtTokenService jwtTokenService = new JwtTokenService();
        AuthenticationStrategy jwtAuthenticationStrategy = new JwtAuthenticationStrategy(userService, jwtTokenService);
        Service userAuthenticationService = new UserAuthenticationService(jwtAuthenticationStrategy);
        Service credentialsExtractor = new BasicCredentialsExtractorService();

        factory.registerService("userService", userService);
        factory.registerService("jwtTokenService", jwtTokenService);
        factory.registerService("userAuthenticationService", userAuthenticationService);
        factory.registerService("credentialsExtractor", credentialsExtractor);
    }
}