package preCapstone.fuseable.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import preCapstone.fuseable.model.oauth.kakao.OauthToken;

@Service
@Slf4j
public class GoogleLoginService {

    private final Environment env;
    private final RestTemplate restTemplate = new RestTemplate();

    public GoogleLoginService(Environment env) {
        this.env = env;
    }

    public void socialLogin(String code) {
        String accessToken = getAccessToken(code);
        System.out.println("accessToken = " + accessToken);
        JsonNode userResourceNode = getUserResource(accessToken);
        System.out.println("userResourceNode = " + userResourceNode);

        String id = userResourceNode.get("id").asText();
        String email = userResourceNode.get("email").asText();
        String nickname = userResourceNode.get("name").asText();
        System.out.println("id = " + id);
        System.out.println("email = " + email);
        System.out.println("nickname = " + nickname);

    }


    private String getAccessToken(String authorizationCode) {
//        String clientId = env.getProperty("oauth2." + "google" + ".192368607699-eon5h3ukefophnirb7brql8f4jr3cu6j.apps.googleusercontent.com");
//        String clientId = env.getProperty("192368607699-eon5h3ukefophnirb7brql8f4jr3cu6j.apps.googleusercontent.com");
//        String clientSecret = env.getProperty("oauth2." + "google" + ".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");//String redirectUri = env.getProperty("oauth2." + "redirect-uri");
//        String tokenUri = env.getProperty("oauth2.https://oauth2.googleapis.com/token");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", "192368607699-eon5h3ukefophnirb7brql8f4jr3cu6j.apps.googleusercontent.com");
        params.add("client_secret", "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");
        params.add("redirect_uri", "http://localhost:8080/login/oauth2/code");
        params.add("grant_type", "authorization_code");


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange("https://oauth2.googleapis.com/token", HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        return accessTokenNode.get("access_token").asText();
    }

    private JsonNode getUserResource(String accessToken) {
//        String resourceUri = env.getProperty("oauth2." + registrationId + ".resource-uri");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange("https://www.googleapis.com/oauth2/v2/userinfo", HttpMethod.GET, entity, JsonNode.class).getBody();

    }
}







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