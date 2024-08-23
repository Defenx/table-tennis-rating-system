package config;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConstantsConfig {
    private final ConfigProperties config;

    /**
     * Returns the URL of the login page.
     *
     * @return the URL of the login page
     */
    public String getLoginURL(){
        return config.getProperty("LOGIN_URL");
    }

    /**
     * Returns the URL of the registration page.
     *
     * @return the URL of the registration page
     */
    public String getRegistrationURL(){
        return config.getProperty("REGISTRATION_URL");
    }

    /**
     * Returns the path to the login JSP page.
     *
     * @return the path to the login JSP page
     */
    public String getLoginJsp(){
        return config.getProperty("LOGIN_JSP");
    }

    /**
     * Returns the path to the registration JSP page.
     *
     * @return the path to the registration JSP page
     */
    public String getRegistrationJsp(){
        return config.getProperty("REGISTRATION_JSP");
    }

    /**
     * Returns the path to the error JSP page.
     *
     * @return the path to the error JSP page
     */
    public String getErrorJsp(){
        return config.getProperty("ERROR_JSP");
    }

    /**
     * Returns the error message for user addition failure.
     *
     * @return the error message for user addition failure
     */
    public String getErrorUserAddMessage(){
        return config.getProperty("ERROR_USER_ADD_MESSAGE");
    }
}
