package preCapstone.fuseable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import preCapstone.fuseable.model.oauth.kakao.OauthToken;
import preCapstone.fuseable.service.GoogleLoginService;

@RestController
@RequestMapping(value = "/login/oauth2")
public class GoogleLoginController {

    GoogleLoginService googleLoginService;

    public GoogleLoginController(GoogleLoginService googleLoginService) {
        this.googleLoginService = googleLoginService;
    }

    @GetMapping("/code")
    public void googleLogin(@RequestParam String code) {
        googleLoginService.socialLogin(code);

    }
}
