package preCapstone.fuseable.config.jwt;


public interface JwtProperties { //(1)
    String SECRET = "bang"; //(2)
    int EXPIRATION_TIME =  864000000; //(3)
    String TOKEN_PREFIX = "Bearer "; //(4)
    String HEADER_STRING = "Authorization"; //(5)
}

