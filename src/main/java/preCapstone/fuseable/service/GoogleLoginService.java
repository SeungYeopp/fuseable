package preCapstone.fuseable.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import preCapstone.fuseable.config.jwt.JwtProperties;
import preCapstone.fuseable.model.oauth.google.GoogleProfile;
import preCapstone.fuseable.model.oauth.kakao.KakaoProfile;
import preCapstone.fuseable.model.oauth.kakao.OauthToken;
import preCapstone.fuseable.model.user.User;
import preCapstone.fuseable.repository.user.UserRepository;

import java.util.Date;


@Service
@Slf4j
public class GoogleLoginService {

    @Autowired
    UserRepository userRepository;
    private final Environment env;
    private final RestTemplate restTemplate = new RestTemplate();

    public GoogleLoginService(Environment env) {
        this.env = env;
    }

    public String socialLogin(String code) {

        String accessToken = getAccessToken(code);

        return saveUserAndGetToken(accessToken);
    }


    private String getAccessToken(String authorizationCode) {

        //Accesstoken을 얻기위한 필요한 parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", "192368607699-eon5h3ukefophnirb7brql8f4jr3cu6j.apps.googleusercontent.com");
        params.add("client_secret", "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");
        params.add("redirect_uri", "http://localhost:8080/login/oauth2/code");
        params.add("grant_type", "authorization_code");


        //보낼 Http 데이터 준비
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //파라미터와 머리
        HttpEntity entity = new HttpEntity(params, headers);

        //uri는 해당 http가 가야할 장소
        ResponseEntity<JsonNode> responseNode = restTemplate.exchange("https://oauth2.googleapis.com/token", HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        return accessTokenNode.get("access_token").asText();
    }

    private JsonNode getUserResource(String accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange("https://www.googleapis.com/oauth2/v2/userinfo", HttpMethod.GET, entity, JsonNode.class).getBody();

    }

    public String saveUserAndGetToken(String token) {

        JsonNode userResourceNode = getUserResource(token);


        User user = userRepository.findByAccountEmail(userResourceNode.get("email").asText());
        if (user == null) {
            user = User.builder()
                    .accountId(userResourceNode.get("id").asLong())
                    .accountProfileImg(userResourceNode.get("picture").asText())
                    .accountNickname(userResourceNode.get("name").asText())
                    .accountEmail(userResourceNode.get("emial").asText())
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
}

//    public GoogleProfile findProfile(String token) {
//
//        RestTemplate rt = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + token); //(1-4)
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
//                new HttpEntity<>(headers);
//
//        // Http 요청 (POST 방식) 후, response 변수에 응답을 받음
//        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
//                "https://kapi.kakao.com/v2/user/me",
//                HttpMethod.POST,
//                kakaoProfileRequest,
//                String.class
//        );
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        GoogleProfile googleProfile = null;
//        try {
//            googleProfile = objectMapper.readValue(GoogleProfileResponse.getBody(), GoogleProfile.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        return googleProfile;
//    }








//    public OauthToken getAccessToken(String code) {
//
//        //http 통신 관련 범용 라이브러리, 애플리케이션 내부 REST API 요청
//        RestTemplate rt = new RestTemplate();
//
//        //http 헤더 오브젝트 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        //http 바디 오브젝트 생성, kakao developer에 있는 값
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", "authorization_code");
//        params.add("client_id", "192368607699-eon5h3ukefophnirb7brql8f4jr3cu6j.apps.googleusercontent.com");
//        params.add("client_secret", "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");
//        params.add("redirect_uri", "http://localhost:8080/login/oauth2/code");
//        params.add("code", code);
//
//        //HttpEntity 생성, header + body를 하나에 담음
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
//                new HttpEntity<>(params, headers);
//
//        //Json 형태이기 때문에 string으로 받음, .exchagne(요청 url, httpmethod.형식, Entity, 타입)으로 api 호출
//        ResponseEntity<String> accessTokenResponse = rt.exchange(
//                "https://oauth2.googleapis.com/token",
//                HttpMethod.POST,
//                kakaoTokenRequest,
//                String.class
//        );
//
//        //json object간의 변환
//        ObjectMapper objectMapper = new ObjectMapper();
//        OauthToken oauthToken = null;
//        try {
//            //json -> OauthToken이라는 object로 변환
//            oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        return oauthToken; //문제 없으면 리턴
//    }
//
//
//    }




//import com.fasterxml.jackson.databind.JsonNode;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.env.Environment;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import preCapstone.fuseable.model.user.UserResource;
//
//@Service
//@Slf4j
//public class GoogleLoginService {
//
//    private final Environment env;
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    public GoogleLoginService(Environment env) {
//        this.env = env;
//    }
//
//    public void socialLogin(String code, String registrationId) {
//        log.info("======================================================");
//        String accessToken = getAccessToken(code, registrationId);
//        JsonNode userResourceNode = getUserResource(accessToken, registrationId);
//
//        UserResource userResource = new UserResource();
//        log.info("userResource = {}", userResource);
//        switch (registrationId) {
//            case "google": {
//                userResource.setId(userResourceNode.get("id").asText());
//                userResource.setEmail(userResourceNode.get("email").asText());
//                userResource.setNickname(userResourceNode.get("name").asText());
//                break;
//            } case "kakao": {
//                userResource.setId(userResourceNode.get("id").asText());
//                userResource.setEmail(userResourceNode.get("kakao_account").get("email").asText());
//                userResource.setNickname(userResourceNode.get("kakao_account").get("profile").get("nickname").asText());
//                break;
//            } case "naver": {
//                userResource.setId(userResourceNode.get("response").get("id").asText());
//                userResource.setEmail(userResourceNode.get("response").get("email").asText());
//                userResource.setNickname(userResourceNode.get("response").get("nickname").asText());
//                break;
//            } default: {
//                throw new RuntimeException("UNSUPPORTED SOCIAL TYPE");
//            }
//        }
//        log.info("id = {}", userResource.getId());
//        log.info("email = {}", userResource.getEmail());
//        log.info("nickname {}", userResource.getNickname());
//        log.info("======================================================");
//    }
//
//    private String getAccessToken(String authorizationCode, String registrationId) {
//        String clientId = env.getProperty("oauth2." + registrationId + ".client-id");
//        String clientSecret = env.getProperty("oauth2." + registrationId + ".client-secret");
//        String redirectUri = env.getProperty("oauth2." + registrationId + ".redirect-uri");
//        String tokenUri = env.getProperty("oauth2." + registrationId + ".token-uri");
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("code", authorizationCode);
//        params.add("client_id", clientId);
//        params.add("client_secret", clientSecret);
//        params.add("redirect_uri", redirectUri);
//        params.add("grant_type", "authorization_code");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        HttpEntity entity = new HttpEntity(params, headers);
//
//        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
//        JsonNode accessTokenNode = responseNode.getBody();
//        return accessTokenNode.get("access_token").asText();
//    }
//
//    private JsonNode getUserResource(String accessToken, String registrationId) {
//        String resourceUri = env.getProperty("oauth2."+registrationId+".resource-uri");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + accessToken);
//        HttpEntity entity = new HttpEntity(headers);
//        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
//    }
//}