package preCapstone.fuseable.model.oauth;

import lombok.Data;

@Data
public class OauthToken { //OauthToken클래스 안의 내용

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;

}
