package preCapstone.fuseable.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import preCapstone.fuseable.dto.user.GoogleOAuthTokenDto;
import preCapstone.fuseable.dto.user.GoogleUserInfoDto;
import preCapstone.fuseable.model.oauth.google.GoogleOAuth;
import preCapstone.fuseable.model.user.User;
import preCapstone.fuseable.repository.user.UserRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GoogleUserService {

    private final UserRepository userRepository;

    private GoogleUserInfoDto getGoogleUserInfoDto(String code) throws JsonProcessingException {
        ResponseEntity<String> accessTokenResponse = googleOAuth.requestAccessToken(code);
        GoogleOAuthTokenDto oAuthToken = googleOAuth.getAccessToken(accesssTokenResponse);
        ResponseEntity<String> userInfoResponse = googleOAuth.requestUserInfo(oAuthToken);
        GoogleUserInfoDto googleUser = googleOAuth.getUserInfo(userInfoResponse);
        return googleUser;
    }


    public SingleResult<TokenUserDto> googlelogin(String code) throws IOException {
        GoogleUserInfoDto googleUser = getGoogleUserInfoDto(code);

        //여기서 getmail이 아닌 다른것으로 바꾸면 save될듯
        //카카오는 ID가 Long, 구글은 string상태
        //구분 필요
        if(!userRepository.existsById(googleUser.getEmail())) {
            userRepository.save(
                    User.builder()
                            .kakaoId(googleUser.getId())
                            .kakaoProfileImg(googleUser.getPicture())
                            .kakaoNickname(googleUser.getName())
                            .kakaoEmail(googleUser.getEmail())
                            .userRole("ROLE_USER").build()
            );

            //여기서도 이메일, ID등 결정필요
            return getSingleResult(userRepository.findById(googleUser.getEmail()
            ).orElseThrow(CEmailsigninFailedException::new));
        }

        //여기서도 이메일, ID등 결정필요
        return getSingleResult(userRepository.findById(googleUser.getEmail()
        ).orElseThrow(CEmailsigninFailedException::new));

    }





}
