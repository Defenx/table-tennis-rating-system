package constant;

public final class SessionAttributes {

    public static final String USER_SESSION_ATTRIBUTE = "user";
    public static final String CSRF_TOKEN_CACHE_ATTRIBUTE = "csrfSaltCache";
    public static final String CSRF_TOKEN_ATTRIBUTE = "csrfToken";

    private SessionAttributes() {
    }
}
