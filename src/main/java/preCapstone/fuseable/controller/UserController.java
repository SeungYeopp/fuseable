package preCapstone.fuseable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import preCapstone.fuseable.config.jwt.JwtProperties;
import org.springframework.http.*;
//import preCapstone.fuseable.model.oauth.google.GoogleOAuth;
import preCapstone.fuseable.model.user.User;
import preCapstone.fuseable.model.oauth.kakao.OauthToken;
import preCapstone.fuseable.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController       //jackson 관련 라이브러리 사용시 사용
@RequestMapping("/api")   // localhost:8080/api 형태로 매핑 됨
public class UserController {

    @Autowired  //자동으로 각 상황의 타입에 맞는 bean을 주입
    private UserService userService;

    // 프론트에서 인가코드 받아오는 url
    @GetMapping("/oauth/token")  //url mapping
    public ResponseEntity getLogin(@RequestParam("code") String code) {

        //인가코드를 통해 access token발행으로 발급
        OauthToken oauthToken = userService.getAccessToken(code);

        // 발급받은 acess token을 통해  DB에 카카오 정보 저장 후 JWT 생성
        String jwtToken = userService.saveUserAndGetToken(oauthToken.getAccess_token());

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

        return ResponseEntity.ok().headers(headers).body("success");
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getCurrentUser(HttpServletRequest request) { //httpServletRequest를 받아옴

        //userService에 해당 메소드 만듬
        User user = userService.getUser(request);

        //ResponseEntity를 이용해 body 값에 인증된 사용자 정보를 넘겨준다.
        return ResponseEntity.ok().body(user);
    }


}
