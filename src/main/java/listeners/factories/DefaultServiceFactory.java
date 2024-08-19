package listeners.factories;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import services.*;

public class DefaultServiceFactory implements ServiceFactory {

    @Override
    public BCryptPasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public LoginService createLoginService(BCryptPasswordEncoder passwordEncoder) {
        return new UserLoginService(passwordEncoder);
    }

    @Override
    public UserAuthenticationService createUserAuthenticationService(LoginService loginService) {
        return new UserAuthManager(loginService);
    }

    @Override
    public CredentialsExtractor createCredentialsExtractor() {
        return new BasicCredentialsExtractor();
    }
}