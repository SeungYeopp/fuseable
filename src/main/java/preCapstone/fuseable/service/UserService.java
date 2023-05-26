package preCapstone.fuseable.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import preCapstone.fuseable.config.jwt.JwtProperties;
import preCapstone.fuseable.model.oauth.kakao.KakaoProfile;
import preCapstone.fuseable.model.oauth.kakao.OauthToken;
import preCapstone.fuseable.model.user.User;
import preCapstone.fuseable.repository.user.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository; //userRepository 연동

    //OAuth -> jwt
    public OauthToken getAccessToken(String code) {

        //http 통신 관련 범용 라이브러리, 애플리케이션 내부 REST API 요청
        RestTemplate rt = new RestTemplate();

        //http 헤더 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //http 바디 오브젝트 생성, kakao developer에 있는 값
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "a8c0f54a40e17de117e2f618394a3e20");
        params.add("redirect_uri", "http://fuseable.monster/redirect");
        params.add("code", code);
//         params.add("client_secret", "bang"); // 생략 가능!

        //HttpEntity 생성, header + body를 하나에 담음
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        //Json 형태이기 때문에 string으로 받음, .exchagne(요청 url, httpmethod.형식, Entity, 타입)으로 api 호출
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        //json object간의 변환
        ObjectMapper objectMapper = new ObjectMapper();
        OauthToken oauthToken = null;
        try {
            //json -> OauthToken이라는 object로 변환
            oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return oauthToken; //문제 없으면 리턴
    }

    public String saveUserAndGetToken(String token) {

        KakaoProfile profile = findProfile(token);

        User user = userRepository.findByAccountEmail(profile.getKakao_account().getEmail());
        if(user == null) {
            user = User.builder()
                    .accountId(profile.getId())
                    .accountProfileImg(profile.getKakao_account().getProfile().getProfile_image_url())
                    .accountNickname(profile.getKakao_account().getProfile().getNickname())
                    .accountEmail(profile.getKakao_account().getEmail())
                    //나중에 한번보기
                    .userRole("ROLE_USER").build();

            userRepository.save(user);
        }

        return createToken(user);
    }

    public String createToken(User user) {

        //java-jwt 라이브러리 사용
        String jwtToken = JWT.create()

                //jwt payload에 들어갈 클레임 설정
                //sub는 자유롭게 설정, exp는 jwt interface에 설정한 만료시간을 가져와 사용
                .withSubject(user.getAccountEmail())
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION_TIME))

                //개인 클레임 설정
                //.withClaim("이름",내용)꼴로 작성, user model파일에서 들고옴
                .withClaim("id", user.getUserCode())
                .withClaim("nickname", user.getAccountNickname())

                //signature 설정 jwt interface의 비밀키 (현재는 bang)을 넣어 해쉬 알고리즘으로 돌림
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return jwtToken;
    }

    public User getUser(HttpServletRequest request) { //HttpServletRequest를 파라미터로 받음

        //request에는 JwtRequestFilter를 거쳐 인증된 사용자의 usercode가 들어가있으니 사용
        Long userCode = (Long) request.getAttribute("userCode");

        //usercode로 DB에서 사용자 정보를 가져와 User에 담음
        User user = userRepository.findByUserCode(userCode);

        //객체 반환
       return user;
    }


    public KakaoProfile findProfile(String token) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token); //(1-4)
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        // Http 요청 (POST 방식) 후, response 변수에 응답을 받음
        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfile;
    }


}
