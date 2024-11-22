package library.booklet.service.account;

import library.booklet.dto.LoginInputDTO;
import library.booklet.dto.UserDTO;
import library.booklet.entity.UserEntity;
import library.booklet.mapper.UserMapper;
import library.booklet.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    void saveUser() {
        UserDTO userDTO = new UserDTO();
        UserEntity userEntity = new UserEntity();
        String encodedPass = "encoded";
        userEntity.setPassword(encodedPass);

        stub_saveUser(userDTO, userEntity, encodedPass);

        UserEntity result = userService.saveUser(userDTO);
        assertThat(result).isEqualTo(userEntity);
    }

    void stub_saveUser(UserDTO userDTO, UserEntity userEntity, String encodedPass) {
        when(bCryptPasswordEncoder.encode(any())).thenReturn(encodedPass);
        when(userRepository.saveAndFlush(eq(userEntity))).thenReturn(userEntity);
        when(userMapper.from(eq(userEntity))).thenReturn(userDTO);
    }

    @Test
    void happyPath_login() {
        LoginInputDTO loginInputDTO = new LoginInputDTO();
        UserEntity userEntity = new UserEntity();
        stub_login(userEntity);

        boolean result = userService.login(loginInputDTO);
        assertThat(result).isTrue();
    }

    void stub_login(UserEntity userEntity) {
        when(userRepository.findByEmail(any())).thenReturn(userEntity);
        when(bCryptPasswordEncoder.matches(any(), any())).thenReturn(true);
    }
}