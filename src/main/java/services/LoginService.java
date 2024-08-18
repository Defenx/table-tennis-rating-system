package services;

import dto.UserDto;

import java.util.Optional;

public interface LoginService {
    Optional<UserDto> getExistedUser(String username, String password);
}
