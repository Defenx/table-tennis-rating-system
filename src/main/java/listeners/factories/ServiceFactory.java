package listeners.factories;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import services.login.CredentialsExtractor;
import services.login.LoginService;
import services.login.auth.strategies.AuthenticationStrategy;
import services.login.auth.JwtTokenService;

public interface ServiceFactory {
    BCryptPasswordEncoder createPasswordEncoder();
    LoginService createLoginService(BCryptPasswordEncoder passwordEncoder);
    AuthenticationStrategy createUserAuthenticationService(LoginService loginService, JwtTokenService jwtTokenService, String strategyType);
    CredentialsExtractor createCredentialsExtractor();
    JwtTokenService createJwtTokenService();
}