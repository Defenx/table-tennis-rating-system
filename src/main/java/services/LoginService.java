package services;

import dto.UserDto;

import java.util.Optional;

/**
 * Интерфейс для сервиса входа в систему.
 * Обеспечивает метод для получения существующего пользователя по имени пользователя и паролю.
 */
public interface LoginService {
    /**
     * Возвращает существующего пользователя на основе имени пользователя и пароля.
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     * @return объект {@link Optional}, содержащий {@link UserDto}, если пользователь найден, или пустой объект, если пользователь не найден
     */
    Optional<UserDto> getExistedUser(String username, String password);
}
