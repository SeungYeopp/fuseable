package preCapstone.fuseable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import preCapstone.fuseable.config.jwt.JwtProperties;
import preCapstone.fuseable.model.oauth.kakao.OauthToken;
import preCapstone.fuseable.service.GoogleLoginService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/login/oauth2")
public class GoogleLoginController {

    GoogleLoginService googleLoginService;

    public GoogleLoginController(GoogleLoginService googleLoginService) {
        this.googleLoginService = googleLoginService;
    }

    @GetMapping("/code")
    public ResponseEntity googleLogin(@RequestParam("code") String code) {
        String jwtToken = googleLoginService.socialLogin(code);

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

        return ResponseEntity.ok().headers(headers).body("success");

    }
}
