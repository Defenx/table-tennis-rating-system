package enums;

import java.util.Set;

/**
 * The type Permission group.
 */
public class PermissionGroup {
    /**
     * The constant PERMIT_ALL.
     */
    public static final Set<Role> PERMIT_ALL = Set.of(Role.USER, Role.ADMIN);
    /**
     * The constant ONLY_ADMIN.
     */
    public static final Set<Role> ONLY_ADMIN = Set.of(Role.ADMIN);
}
