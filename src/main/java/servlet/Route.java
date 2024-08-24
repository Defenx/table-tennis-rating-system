package servlet;

public class Route {
    private static final String JSP_FOLDER_PATH = "/WEB-INF/views";
    private static final String JSP_EXTENSION = ".jsp";

    public static final String REGISTRATION = "/test-registration";
    public static final String REGISTRATION_JSP = getJspPath(REGISTRATION);
    public static final String SUCCESS_REGISTRATION = "/success-test-registration";
    public static final String SUCCESS_REGISTRATION_JSP = getJspPath(SUCCESS_REGISTRATION);

    private static String getJspPath(String endpoint) {
        return JSP_FOLDER_PATH + endpoint + JSP_EXTENSION;
    }
}
