package woowahan.anifarm.tecolearning.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowahan.anifarm.tecolearning.auth.advice.LoggedInUser;
import woowahan.anifarm.tecolearning.user.dto.UserCreateDto;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;
import woowahan.anifarm.tecolearning.user.dto.UserUpdateDto;
import woowahan.anifarm.tecolearning.user.service.UserService;

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

    @GetMapping("/myinfo")
    public ResponseEntity<UserInfoDto> read(@LoggedInUser UserInfoDto userInfoDto) {
        return ResponseEntity.ok().body(userService.findInfoDtoById(userInfoDto.getId()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoDto> read(@PathVariable long userId) {
        return ResponseEntity.ok().body(userService.findInfoDtoById(userId));
    }

    @PutMapping
    public ResponseEntity<UserInfoDto> update(@RequestBody UserUpdateDto userUpdateDto, @LoggedInUser UserInfoDto userInfoDto) {
        return ResponseEntity.ok().body(userService.update(userUpdateDto, userInfoDto.getId()));
    }

    @DeleteMapping
    public ResponseEntity delete(@LoggedInUser UserInfoDto userInfoDto) {
        userService.delete(userInfoDto.getId());

        return ResponseEntity.ok().build();
    }
}