package servlet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Route {
    TEST_REGISTRATION_PAGE("/WEB-INF/views/test-registration.jsp", "/test-registration"),
    SUCCESS_TEST_REGISTRATION_PAGE("/WEB-INF/views/success-test-registration.jsp", "/success-test-registration");

    private final String jspPath;
    private final String uri;
}
