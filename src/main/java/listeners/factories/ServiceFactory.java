package listeners.factories;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import services.*;

public interface ServiceFactory {
    BCryptPasswordEncoder createPasswordEncoder();
    LoginService createLoginService(BCryptPasswordEncoder passwordEncoder);
    UserAuthenticationService createUserAuthenticationService(LoginService loginService);
    CredentialsExtractor createCredentialsExtractor();
}