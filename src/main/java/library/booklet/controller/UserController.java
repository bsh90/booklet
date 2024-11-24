package library.booklet.controller;

import library.booklet.dto.LoginInputDTO;
import library.booklet.dto.UserDTO;
import library.booklet.entity.UserEntity;
import library.booklet.service.account.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/saveUser")
    public ResponseEntity<?> saveUser(UserDTO userDto) {
        return userService.saveUser(userDto);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(LoginInputDTO loginInputDTO) {
        return userService.login(loginInputDTO);
    }

    @GetMapping("/findAllUser")
    public ResponseEntity<List<UserEntity>> findAllUsers() {
        List<UserEntity> userEntities = userService.findAllUsers();
        return ResponseEntity.ok(userEntities);
    }
}
