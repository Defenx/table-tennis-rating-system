package enums;

import java.util.Set;

public class PermissionGroup {

    public static final Set<Role> PERMIT_ALL = Set.of(Role.USER, Role.ADMIN);

    public static final Set<Role> ONLY_ADMIN = Set.of(Role.ADMIN);
}
