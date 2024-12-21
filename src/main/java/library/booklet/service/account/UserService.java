package library.booklet.service.account;

import org.apache.commons.validator.routines.EmailValidator;
import library.booklet.dto.LoginInputDTO;
import library.booklet.dto.UserDTO;
import library.booklet.entity.UserEntity;
import library.booklet.mapper.UserMapper;
import library.booklet.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public ResponseEntity<?> createUser(UserDTO userDto) {
        UserEntity foundUser = userRepository.findByEmail(userDto.getEmail());
        if (foundUser != null) {
            return ResponseEntity.status(400).body("The email already exists in the database!");
        }
        boolean validEmail = EmailValidator.getInstance().isValid(userDto.getEmail());
        if (!validEmail) {
            return ResponseEntity.status(400).body("The email is not valid!");
        }

        UserEntity user = new UserEntity();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserEntity userEntity = userRepository.saveAndFlush(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userEntity);
    }

    public List<UserEntity> findAllUsers() {
        return userRepository.findAll(Sort.by("email"));
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public ResponseEntity<?> login(LoginInputDTO loginInputDTO) {
        boolean validEmail = EmailValidator.getInstance().isValid(loginInputDTO.getEmail());
        if (!validEmail) {
            return ResponseEntity.status(400).body("The email is not valid!");
        }
        UserEntity userEntity = userRepository.findByEmail(loginInputDTO.getEmail());
        Boolean isLogin = passwordEncoder.matches(loginInputDTO.getPassword(), userEntity.getPassword());
        return ResponseEntity.ok(isLogin);
    }
}
