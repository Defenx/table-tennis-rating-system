package config;

public class ConstantsConfig {
    private final Config config;


    public ConstantsConfig() {
        this.config = new Config();
    }

    public String getLoginURL(){
        return config.getProperty("LOGIN_URL");
    }

    public String getRegistrationURL(){
        return config.getProperty("REGISTRATION_URL");
    }

    public String getLoginJsp(){
        return config.getProperty("LOGIN_JSP");
    }

    public String getRegistrationJsp(){
        return config.getProperty("REGISTRATION_JSP");
    }

    public String getErrorJsp(){
        return config.getProperty("ERROR_JSP");
    }

    public String getErrorUserAddMessage(){
        return config.getProperty("ERROR_USER_ADD_MESSAGE");
    }
}
