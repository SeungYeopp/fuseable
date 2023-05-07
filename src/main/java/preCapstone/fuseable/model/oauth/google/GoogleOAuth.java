package preCapstone.fuseable.model.oauth.google;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import preCapstone.fuseable.dto.user.GoogleOAuthTokenDto;
import preCapstone.fuseable.dto.user.GoogleUserInfoDto;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GoogleOAuth {

    private final String googleLoginUrl = "https://accounts.google.com";
    private final String GOOGLE_TOKEN_REQUEST_URL = "https://oauth2.googleapis.com/token";
    private final String GOOGLE_USERINFO_REQUEST_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;


    @Value("${app.google.clientId}")
    private String googleClientId;
    @Value("${app.google.redirect}")
    private String googleRedirectUrl;
    @Value("${app.google.secret}")
    private String googleClientSecret;


    public ResponseEntity<String> requestAccessToken(java.lang.String code) {
        RestTemplate restTemplate = new RestTemplate();
        Map<java.lang.String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", "192368607699-eon5h3ukefophnirb7brql8f4jr3cu6j.apps.googleusercontent.com");
        params.put("client_secret", "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");
        params.put("redirect_uri", "http://localhost:8080/login/oauth2/code/google");
        params.put("grant_type", "authorization_code");

        ResponseEntity<java.lang.String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_REQUEST_URL, params, java.lang.String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity;
        }

        return null;
    }

    public GoogleOAuthTokenDto getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        System.out.println("response.getBody() =" + response.getBody());
        GoogleOAuthTokenDto googleOAuthTokenDto = objectMapper.readValue(response.getBody(), GoogleOAuthTokenDto.class);
        return googleOAuthTokenDto;
    }

    public ResponseEntity<String> requestUserInfo(GoogleOAuthTokenDto oAuthToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oAuthToken.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(GOOGLE_USERINFO_REQUEST_URL, HttpMethod.GET, request, String.class);
        return response;
    }

    public GoogleUserInfoDto getUserInfo(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper1 = new ObjectMapper();
        GoogleUserInfoDto googleUserInfoDto = objectMapper.readValue(response.getBody(),GoogleUserInfoDto.class);
        return googleUserInfoDto;
    }



}
