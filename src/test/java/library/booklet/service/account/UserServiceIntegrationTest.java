package library.booklet.service.account;

import library.booklet.dto.LoginInputDTO;
import library.booklet.dto.UserDTO;
import library.booklet.entity.UserEntity;
import library.booklet.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestRestTemplate testRestTemplate;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest");

    @BeforeAll
    static void beforeAll() {
        mysql.start();
    }

    @AfterAll
    static void afterAll() {
        mysql.stop();
    }

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto",() -> "create");
    }

    @BeforeEach
    void setUp() {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("B");
        userEntity.setLastName("Sh");
        userEntity.setEmail("bsh@yahoo.com");
        userEntity.setPassword(passwordEncoder.encode("pass"));
        userRepository.save(userEntity);
    }

    @AfterEach
    public void afterEach(){
        userRepository.deleteAll();
    }

    @Test
    void happyPath_findAllUser() {
        ResponseEntity<UserEntity[]> response = testRestTemplate.getForEntity("/findAllUser", UserEntity[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isEqualTo(1);
        Arrays.stream(response.getBody()).forEach(userEntity -> {
            assertThat(userEntity.getEmail()).isEqualTo("bsh@yahoo.com");
        });
    }

    @Test
    void happyPath_saveUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("B1");
        userDTO.setLastName("Sh1");
        String email = "bshid@yahoo.com";
        userDTO.setEmail(email);
        userDTO.setPassword("pass1");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO, headers);

        ResponseEntity<UserEntity> response = testRestTemplate.postForEntity("/saveUser", request, UserEntity.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEmail()).isEqualTo(email);

    }

    @Test
    void invalidEmail_saveUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("B1");
        userDTO.setLastName("Sh1");
        String email = "bshidyahoo.com";
        userDTO.setEmail(email);
        userDTO.setPassword("pass1");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO, headers);

        ResponseEntity<String> response = testRestTemplate.postForEntity("/saveUser", request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo("The email is not valid!");
    }

    @Test
    void repeatedEmail_saveUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("B1");
        userDTO.setLastName("Sh1");
        String email = "bsh@yahoo.com";
        userDTO.setEmail(email);
        userDTO.setPassword("pass1");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO, headers);

        ResponseEntity<String> response = testRestTemplate.postForEntity("/saveUser", request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo("The email already exists in the database!");
    }

    @Test
    void happyPath_login() {
        LoginInputDTO loginInputDTO = new LoginInputDTO("bsh@yahoo.com", "pass");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<LoginInputDTO> request = new HttpEntity<>(loginInputDTO, headers);

        ResponseEntity<Boolean> response = testRestTemplate.postForEntity("/login", request, Boolean.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isTrue();
    }

    @Test
    void false_login() {
        LoginInputDTO loginInputDTO = new LoginInputDTO("bsh@yahoo.com", "differentPass");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<LoginInputDTO> request = new HttpEntity<>(loginInputDTO, headers);

        ResponseEntity<Boolean> response = testRestTemplate.postForEntity("/login", request, Boolean.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isFalse();
    }

    @Test
    void incorrectEmail_login() {
        LoginInputDTO loginInputDTO = new LoginInputDTO("bshyahoo.com", "pass");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<LoginInputDTO> request = new HttpEntity<>(loginInputDTO, headers);

        ResponseEntity<String> response = testRestTemplate.postForEntity("/login", request, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo("The email is not valid!");
    }
}
