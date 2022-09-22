package preCapstone.fuseable.config.jwt;


public interface JwtProperties { //인터페이스 내 정의되는 필드는 public static final이 붙음
    String SECRET = "bang"; //비밀키 영어로 작성
    int EXPIRATION_TIME =  864000000; //토큰 만료 기간, 현재 10일
    String TOKEN_PREFIX = "Bearer "; //토큰 앞에 붙는 형식
    String HEADER_STRING = "Authorization";
}

