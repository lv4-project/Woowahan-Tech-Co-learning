package woowahan.anifarm.tecolearning.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowahan.anifarm.tecolearning.user.dto.UserCreateDto;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;
import woowahan.anifarm.tecolearning.user.dto.UserLoginDto;
import woowahan.anifarm.tecolearning.user.dto.UserUpdateDto;
import woowahan.anifarm.tecolearning.user.service.UserService;

import javax.servlet.http.HttpSession;

import static woowahan.anifarm.tecolearning.user.service.UserService.LOGGED_IN_USER_SESSION_KEY;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserInfoDto> create(@RequestBody UserCreateDto userCreateDto) {
        return ResponseEntity.ok().body(userService.save(userCreateDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoDto> read(@PathVariable long userId) {
        return ResponseEntity.ok().body(userService.findInfoDtoById(userId));
    }

    @PutMapping
    public ResponseEntity<UserInfoDto> update(@RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok().body(userService.update(userUpdateDto, 1));
    }

    @PostMapping("/login")
    public ResponseEntity<UserInfoDto> login(@RequestBody UserLoginDto userLoginDto, HttpSession httpSession) {
        UserInfoDto userInfoDto = userService.authenticate(userLoginDto);
        httpSession.setAttribute(LOGGED_IN_USER_SESSION_KEY, userInfoDto.getId());
        return ResponseEntity.ok().body(userInfoDto);
    }

    @DeleteMapping
    public ResponseEntity delete() {
        userService.delete(1);

        return ResponseEntity.ok().build();
    }
}