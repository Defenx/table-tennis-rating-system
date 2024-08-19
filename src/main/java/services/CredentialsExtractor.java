package services;

import dto.Credentials;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Интерфейс для извлечения учетных данных из HTTP-запроса.
 */
public interface CredentialsExtractor {
    /**
     * Извлекает учетные данные из HTTP-запроса.
     *
     * @param request HTTP-запрос, содержащий учетные данные
     * @return объект {@link Credentials}, содержащий извлеченные учетные данные
     */
    Credentials extract(HttpServletRequest request);
}
