package servlet;

public class Route {
    private static final String JSP_EXTENSION = ".jsp";

    public static final String LOGIN = "/login";
    public static final String LOGIN_JSP = getJspPath(LOGIN);

    public static final String REGISTRATION = "/registration";
    public static final String REGISTRATION_JSP = getJspPath(REGISTRATION);

    public static final String HOME = "/home";
    public static final String HOME_JSP = getJspPath(HOME);

    public static final String ERROR = "/error";
    public static final String ERROR_JSP = getJspPath(ERROR);

    public static final String DELETE_BY_PARTICIPANT_ID = "/participant/delete/*";

    public static String getJspPath(String endpoint) {
        return endpoint + JSP_EXTENSION;
    }
}
