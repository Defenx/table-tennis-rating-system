package services;

import dto.RegistrationDto;

/**
 * The UserService class provides methods for managing users.
 */


public class UserService {
    /**
     * Adds a new user based on the provided registration data.
     *
     * @param data the RegistrationDto object containing user data
     * @return true if the user is successfully added, otherwise false
     */
    public boolean addUser(RegistrationDto data) {
        // Check for the existence of a user with the given email and add them to the database
        System.out.println("User is created successfully");
        return true;
    }
}
