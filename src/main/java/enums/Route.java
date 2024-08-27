package enums;

import constant.RouteConstants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public enum Route {
    LOGIN(
            RouteConstants.LOGIN,
            (RouteConstants.LOGIN + ".jsp"),
            Role.ALL_ROLES,
            false),
    REGISTRATION(
            RouteConstants.REGISTRATION,
            (RouteConstants.REGISTRATION + ".jsp"),
            Role.ALL_ROLES,
            false),
    ERROR(
            RouteConstants.ERROR,
            (RouteConstants.ERROR + ".jsp"),
            Role.ALL_ROLES,
            false),
    ADMIN_TOURNAMENT_CREATE(
            RouteConstants.ADMIN_TOURNAMENT_CREATE,
            (RouteConstants.ADMIN_TOURNAMENT_CREATE + ".jsp"),
            Set.of(Role.ADMIN),
            true),
    HOME(
            RouteConstants.HOME,
            (RouteConstants.HOME + ".jsp"),
            Role.ALL_ROLES,
            true
    );

    private final String path;
    private final String jspPath;
    private final Set<Role> allowedRoles;
    private final boolean requiresAuth;

    public static Optional<Route> fromPath(String path) {
        for (Route route : values()) {
            if (route.getPath().equals(path)) {
                return Optional.ofNullable(route);
            }
        }
        return Optional.empty();
    }

    public boolean isAllowedForRole(Role role) {
        return allowedRoles.contains(role);
    }

}