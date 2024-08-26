package servlet;

import enums.Role;

import java.util.Set;

public class Route {
    private static final String JSP_EXTENSION = ".jsp";

    public static final String LOGIN = "/login";
    public static final String LOGIN_JSP = getJspPath(LOGIN);
    public static final Set<Role> LOGIN_ROLES = Set.of(Role.USER, Role.ADMIN);

    public static final String REGISTRATION = "/registration";
    public static final String REGISTRATION_JSP = getJspPath(REGISTRATION);
    public static final Set<Role> REGISTRATION_ROLES = Set.of(Role.USER, Role.ADMIN);

    public static final String ERROR = "/error";
    public static final String ERROR_JSP = getJspPath(ERROR);
    public static final Set<Role> ERROR_ROLES = Set.of(Role.USER, Role.ADMIN);

    public static final String ADMIN_TOURNAMENT_CREATE = "/tournament/create";
    public static final String ADMIN_TOURNAMENT_CREATE_JSP = getJspPath(ADMIN_TOURNAMENT_CREATE);
    public static final Set<Role> ADMIN_TOURNAMENT_CREATE_ROLES = Set.of(Role.ADMIN);

    public static String getJspPath(String endpoint) {
        return endpoint + JSP_EXTENSION;
    }
}
