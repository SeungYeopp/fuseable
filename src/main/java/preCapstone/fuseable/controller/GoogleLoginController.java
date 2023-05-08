package preCapstone.fuseable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import preCapstone.fuseable.service.GoogleLoginService;

@RestController
@RequestMapping(value = "/login/oauth2", produces = "application/json")
public class GoogleLoginController {

    GoogleLoginService googleLoginService;

    public GoogleLoginController(GoogleLoginService googleLoginService) {
        this.googleLoginService = googleLoginService;
    }

    @GetMapping("/code/{registrationId}")
    public void googleLogin(@RequestParam String code, @PathVariable String registrationId) {
        googleLoginService.socialLogin(code, registrationId);
    }
}
