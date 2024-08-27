package enums;

import java.util.Set;

public enum Role {
    USER, ADMIN;

    public static final Set<Role> ALL_ROLES = Set.of(USER, ADMIN);
}
