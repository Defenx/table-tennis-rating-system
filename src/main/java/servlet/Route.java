package servlet;

public class Route {
    private static final String JSP_EXTENSION = ".jsp";

    public static final String LOGIN = "/login";
    public static final String LOGIN_JSP = getJspPath(LOGIN);

    public static final String REGISTRATION = "/registration";
    public static final String REGISTRATION_JSP = getJspPath(REGISTRATION);

    public static final String ERROR = "/error";
    public static final String ERROR_JSP = getJspPath(ERROR);
    public static final String TOURNAMENT = "/tournament_create";
    public static final String TOURNAMENT_JSP = getJspPath(TOURNAMENT);

    public static String getJspPath(String endpoint) {
        return endpoint + JSP_EXTENSION;
    }
}
