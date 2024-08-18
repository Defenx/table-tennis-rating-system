package Services;

import dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LoginService {
    private BCryptPasswordEncoder encoder;

    private List<UserDto> mockedFindAllUsers(){
        return List.of(new UserDto(),new UserDto());
    }

    private List<UserDto> mockedFindUsersByEmail(String email){
        return List.of(new UserDto(),new UserDto());
    }


    public Optional<UserDto> getExistedUser(String email, String password){
        return mockedFindUsersByEmail(email).stream()
                .filter(x->x.getPassword().equals(encoder.encode(password)))
                .findFirst();
    }
}
