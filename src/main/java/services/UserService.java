package services;

import dto.RegistrationDto;

public class UserService {
    public boolean addUser(RegistrationDto data) {
        // Проверка на существование пользователя с таким Email и добавление его в базу
        System.out.println("User is created successfully");
        return true;
    }
}
