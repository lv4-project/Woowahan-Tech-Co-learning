package woowahan.anifarm.tecolearning.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoDto> read(@PathVariable long userId) {
        return ResponseEntity.ok().body(userService.findInfoDtoById(userId));
    }

    @PutMapping
    public ResponseEntity<UserInfoDto> update(@RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok().body(userService.update(userUpdateDto, 1));
    }

    @DeleteMapping
    public ResponseEntity delete() {
        userService.delete(1);

        return ResponseEntity.ok().build();
    }
}