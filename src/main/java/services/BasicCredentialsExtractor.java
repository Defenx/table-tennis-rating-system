package services;

import dto.Credentials;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Класс для извлечения базовых учетных данных.
 * Реализует интерфейс {@link CredentialsExtractor} и извлекает учетные данные пользователя из HTTP-запроса.
 */
public class BasicCredentialsExtractor implements CredentialsExtractor {

    /**
     * Извлекает учетные данные (имя пользователя и пароль) из HTTP-запроса.
     *
     * @param request HTTP-запрос, содержащий учетные данные
     * @return объект {@link Credentials}, содержащий имя пользователя и пароль
     */
    @Override
    public Credentials extract(HttpServletRequest request) {
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        return new Credentials(username, password);
    }
}
