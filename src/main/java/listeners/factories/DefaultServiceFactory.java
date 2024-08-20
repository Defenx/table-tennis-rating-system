package listeners.factories;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import services.login.BasicCredentialsExtractor;
import services.login.CredentialsExtractor;
import services.login.LoginService;
import services.login.UserLoginService;
import services.login.auth.JwtTokenService;
import services.login.auth.strategies.AuthenticationStrategy;
import services.login.auth.strategies.JwtAuthenticationStrategy;
import services.login.auth.strategies.StandardAuthenticationStrategy;

public class DefaultServiceFactory implements ServiceFactory {

    @Override
    public BCryptPasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public LoginService createLoginService(BCryptPasswordEncoder passwordEncoder) {
        return new UserLoginService(passwordEncoder);
    }

    public AuthenticationStrategy createUserAuthenticationService(LoginService loginService,
                                                                  JwtTokenService jwtTokenService,
                                                                  String strategyType) {
        if ("JWT".equalsIgnoreCase(strategyType)) {
            return new JwtAuthenticationStrategy(loginService, jwtTokenService);
        } else {
            return new StandardAuthenticationStrategy(loginService);
        }
    }

    @Override
    public JwtTokenService createJwtTokenService() {
        return new JwtTokenService();
    }

    @Override
    public CredentialsExtractor createCredentialsExtractor() {
        return new BasicCredentialsExtractor();
    }
}