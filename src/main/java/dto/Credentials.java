package dto;

/**
 * Класс для хранения учетных данных.
 *
 * @param username имя пользователя
 * @param password пароль
 */
public record Credentials(String username, String password) {}
