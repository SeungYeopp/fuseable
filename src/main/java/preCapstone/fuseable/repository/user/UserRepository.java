package preCapstone.fuseable.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import preCapstone.fuseable.model.user.User;



                                                                      //jpaRepository에 의해서 @Repository는 따로 필요없음
public interface UserRepository extends JpaRepository<User, Long> {   //DB를 이용하여 Creat,Read,Update,Delete 기능을 함
    // JPA findBy 규칙
    // select * from user_master where kakao_email = ?

    public User findByKakaoEmail(String kakaoEmail);   //findby 규칙에 의해 model-user의 kakaoEmail 기준으로 찾음

    public User findByUserCode(Long userCode);         //findby 규칙에 의해 model-user Usercode 기준으로 찾음
}
