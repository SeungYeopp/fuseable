package preCapstone.fuseable.model.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity                                                  //JPA가 데이터 바인딩
@Data
@NoArgsConstructor                                       //기본 생성자 만들어줌, 갑이 비어도 됨
@Table(name = "user_master")                             //DB의 테이블명과 클래스 이름이 다르기 때문에 @Table 어노테이션
public class User {

    @Id                                                  //PK
    @GeneratedValue(strategy=GenerationType.IDENTITY)  //필드명과 컬럼명이 다를 때 사용 (user code 대 user_code)
    @Column(name = "USER_ID")                          //필드(열) 이름
    private Long userCode;

    @Column(name = "kakao_id")                           //필드(열) 이름
    private Long kakaoId;

    @Column(name = "kakao_profile_img")                  //필드(열) 이름
    private String kakaoProfileImg;

    @Column(name = "kakao_nickname")                     //필드(열) 이름
    private String kakaoNickname;

    @Column(name = "kakao_email")                        //필드(열) 이름
    private String kakaoEmail;

    @Column(name = "user_role")                          //필드(열) 이름
    private String userRole;

    @Column(name = "create_time")                        //필드(열) 이름
    @CreationTimestamp                                   //DB에서 current_timestamp설정시 사용
    private Timestamp createTime;                        //유저 관리용 시간

    @Builder
    public User(Long kakaoId, String kakaoProfileImg, String kakaoNickname, //메소드, 생성자에만 가능
                String kakaoEmail, String userRole) {

        this.kakaoId = kakaoId;
        this.kakaoProfileImg = kakaoProfileImg;
        this.kakaoNickname = kakaoNickname;
        this.kakaoEmail = kakaoEmail;
        this.userRole = userRole;
    }

    public static User of(Long kakaoId, String kakaoProfileImg, String kakaoNickname, String kakaoEmail, String userRole) {
        return new User(kakaoId, kakaoProfileImg, kakaoNickname, kakaoEmail, userRole);
    }
}
