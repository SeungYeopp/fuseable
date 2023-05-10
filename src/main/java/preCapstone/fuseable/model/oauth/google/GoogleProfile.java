package preCapstone.fuseable.model.oauth.google;

import lombok.Data;
import preCapstone.fuseable.model.oauth.kakao.KakaoProfile;

@Data
public class GoogleProfile {
    public Long id;
    public String connected_at;
    public GoogleProperties properties;
    public GoogleAccount kakao_account;

    @Data
    public class GoogleProperties {  //DB 설정할 때 이미지 부분을 Not NULL로 했었다면 이미지 경로 1이 아닌 경로 2에서 가져오기
        public String nickname;
        public String profile_image; // 이미지 경로 필드1 ,NULL인 경우가 있음
        public String thumbnail_image;
    }

    @Data
    public class GoogleAccount {
        public Boolean profile_nickname_needs_agreement;
        public Boolean profile_image_needs_agreement;
        public GoogleProfileDetail profile;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;
    }

        @Data
        public class GoogleProfileDetail {
            public String nickname;
            public String thumbnail_image_url;
            public String profile_image_url; // 이미지 경로 필드2, NULL이 아닐 확률이 높음
            public Boolean is_default_image;
        }

}
