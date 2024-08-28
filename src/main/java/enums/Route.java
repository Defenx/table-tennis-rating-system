package enums;

import constant.RouteConstants;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

/**
 * The enum Route.
 */
@Getter
public enum Route {
    /**
     * Login route.
     */
    LOGIN(
            RouteConstants.LOGIN,
            PermissionGroup.PERMIT_ALL,
            false),
    /**
     * Registration route.
     */
    REGISTRATION(
            RouteConstants.REGISTRATION,
            PermissionGroup.PERMIT_ALL,
            false),
    /**
     * Error route.
     */
    ERROR(
            RouteConstants.ERROR,
            PermissionGroup.PERMIT_ALL,
            false),
    /**
     * Admin tournament create route.
     */
    ADMIN_TOURNAMENT_CREATE(
            RouteConstants.ADMIN_TOURNAMENT_CREATE,
            PermissionGroup.ONLY_ADMIN,
            true),
    /**
     * Home route.
     */
    HOME(
            RouteConstants.HOME,
            PermissionGroup.PERMIT_ALL,
            true
    ),
    /**
     * Root route.
     */
    ROOT(
            RouteConstants.ROOT,
            PermissionGroup.PERMIT_ALL,
            true
    );

    private final String path;
    private final String jspPath;
    private final Set<Role> allowedRoles;
    private final boolean requiresAuth;

    Route(String path, Set<Role> allowedRoles, boolean requiresAuth) {
        this.path = path;
        this.jspPath = path + ".jsp";
        this.allowedRoles = allowedRoles;
        this.requiresAuth = requiresAuth;
    }

    /**
     * From path optional.
     *
     * @param path the path
     * @return the optional
     */
    public static Optional<Route> fromPath(String path) {
        return Arrays.stream(values())
                .filter(route -> (route.getPath().equals(path) || route.getJspPath().equals(path)))
                .findAny();
    }

    /**
     * Is allowed for role boolean.
     *
     * @param role the role
     * @return the boolean
     */
    public boolean isAllowedForRole(Role role) {
        return allowedRoles.contains(role);
    }
}
