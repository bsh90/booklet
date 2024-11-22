package library.booklet.service.account;

import library.booklet.exception.NotValidEmailException;
import org.apache.commons.validator.routines.EmailValidator;
import library.booklet.dto.LoginInputDTO;
import library.booklet.dto.UserDTO;
import library.booklet.entity.UserEntity;
import library.booklet.exception.DuplicateEmailException;
import library.booklet.mapper.UserMapper;
import library.booklet.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserEntity saveUser(UserDTO userDto) {
        validateEmail(userDto);

        UserEntity user = new UserEntity();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userRepository.saveAndFlush(user);
    }

    private void validateEmail(UserDTO userDTO) throws DuplicateEmailException,
            NotValidEmailException{
        UserEntity foundUser = userRepository.findByEmail(userDTO.getEmail());
        if (foundUser != null) {
            throw new DuplicateEmailException("The email is already in database.");
        }
        boolean validEmail = EmailValidator.getInstance().isValid(userDTO.getEmail());
        if (!validEmail) {
            throw new NotValidEmailException("The email is not valid!");
        }
    }

    public List<UserEntity> findAllUsers() {
        return userRepository.findAll(Sort.by("email"));
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public boolean login(LoginInputDTO loginInputDTO) {
        UserEntity userEntity = userRepository.findByEmail(loginInputDTO.getEmail());
        return passwordEncoder.matches(loginInputDTO.getPassword(), userEntity.getPassword());
    }
}
