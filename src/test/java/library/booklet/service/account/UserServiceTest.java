package library.booklet.service.account;

import library.booklet.dto.LoginInputDTO;
import library.booklet.dto.UserDTO;
import library.booklet.entity.UserEntity;
import library.booklet.mapper.UserMapper;
import library.booklet.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    UserService userService;
    UserRepository userRepository;
    UserMapper userMapper;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
        userService = new UserService(userRepository, userMapper, bCryptPasswordEncoder);
    }

    @Test
    void happyPath_createUser() {
        String encodedPass = "encoded";
        String email = "bsh@yahoo.com";
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPassword(encodedPass);

        stub_createUser(userDTO, userEntity, encodedPass);

        ResponseEntity<?> result = userService.createUser(userDTO);
        assertThat(result.getBody()).isEqualTo(userEntity);
    }

    void stub_createUser(UserDTO userDTO, UserEntity userEntity, String encodedPass) {
        when(bCryptPasswordEncoder.encode(any())).thenReturn(encodedPass);
        when(userRepository.saveAndFlush(eq(userEntity))).thenReturn(userEntity);
        when(userMapper.from(eq(userEntity))).thenReturn(userDTO);
    }

    @Test
    void happyPath_login() {
        String email = "bsh@yahoo.com";
        LoginInputDTO loginInputDTO = new LoginInputDTO();
        loginInputDTO.setEmail(email);
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        stub_login(userEntity);

        ResponseEntity<?> result = userService.login(loginInputDTO);
        assertThat(result.getBody()).isEqualTo(true);
    }

    void stub_login(UserEntity userEntity) {
        when(userRepository.findByEmail(any())).thenReturn(userEntity);
        when(bCryptPasswordEncoder.matches(any(), any())).thenReturn(true);
    }
}