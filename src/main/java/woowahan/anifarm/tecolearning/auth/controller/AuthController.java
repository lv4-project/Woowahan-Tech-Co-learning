package woowahan.anifarm.tecolearning.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowahan.anifarm.tecolearning.auth.service.OauthService;
import woowahan.anifarm.tecolearning.user.dto.UserLoginDto;

import javax.servlet.http.HttpServletResponse;

import static woowahan.anifarm.tecolearning.auth.advice.LoggedInInterceptor.createJWTCookie;

@RestController
@RequestMapping("/api/oauth")
public class AuthController {
    private final OauthService oauthService;

    public AuthController(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    @GetMapping
    public ResponseEntity<String> login(@RequestParam String code, HttpServletResponse response) {
        response.addCookie(createJWTCookie(oauthService.gitLogin(code).getToken()));

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto, HttpServletResponse response) {
        response.addCookie(createJWTCookie(oauthService.login(userLoginDto).getToken()));

        return ResponseEntity.ok().build();
    }
}
