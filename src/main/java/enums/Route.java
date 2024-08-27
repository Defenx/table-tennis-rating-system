package enums;

import constant.RouteConstants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public enum Route {
    LOGIN(
            RouteConstants.LOGIN,
            (RouteConstants.LOGIN + ".jsp"),
            Set.of(Role.USER, Role.ADMIN),
            false),
    REGISTRATION(
            RouteConstants.REGISTRATION,
            (RouteConstants.REGISTRATION + ".jsp"),
            Set.of(Role.USER, Role.ADMIN),
            false),
    ERROR(
            RouteConstants.ERROR,
            (RouteConstants.ERROR + ".jsp"),
            Set.of(Role.USER, Role.ADMIN),
            false),
    ADMIN_TOURNAMENT_CREATE(
            RouteConstants.ADMIN_TOURNAMENT_CREATE,
            (RouteConstants.ADMIN_TOURNAMENT_CREATE + ".jsp"),
            Set.of(Role.ADMIN),
            true),
    HOME(
            RouteConstants.HOME,
            (RouteConstants.HOME + ".jsp"),
            Set.of(Role.ADMIN, Role.USER),
            true
    );

    private final String path;
    private final String jspPath;
    private final Set<Role> allowedRoles;
    private final boolean requiresAuth;

    public static Route fromPath(String path) {
        for (Route route : values()) {
            if (route.getPath().equals(path)) {
                return route;
            }
        }
        return null;
    }
}