package enums;

import constant.RouteConstants;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@Getter
public enum Route {
    LOGIN(
            RouteConstants.LOGIN,
            PermissionGroup.PERMIT_ALL,
            false),
    REGISTRATION(
            RouteConstants.REGISTRATION,
            PermissionGroup.PERMIT_ALL,
            false),
    ERROR(
            RouteConstants.ERROR,
            PermissionGroup.PERMIT_ALL,
            false),
    ADMIN_TOURNAMENT_CREATE(
            RouteConstants.ADMIN_TOURNAMENT_CREATE,
            PermissionGroup.ONLY_ADMIN,
            true),
    HOME(
            RouteConstants.HOME,
            PermissionGroup.PERMIT_ALL,
            true
    ),
    ROOT(
            RouteConstants.ROOT,
            PermissionGroup.PERMIT_ALL,
            true
    ),

    DELETE_BY_PARTICIPANT_ID(
            RouteConstants.DELETE_BY_PARTICIPANT_ID,
            PermissionGroup.ONLY_ADMIN,
            true
    ),
    DELETE_TOURNAMENT(
            RouteConstants.DELETE_TOURNAMENT,
            PermissionGroup.ONLY_ADMIN,
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

    public static Optional<Route> fromPath(String path) {
        return Arrays.stream(values())
                .filter(route -> (route.getPath().equals(path) || route.getJspPath().equals(path)))
                .findAny();
    }

    public boolean isAllowedForRole(Role role) {
        return allowedRoles.contains(role);
    }
}
