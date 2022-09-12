package preCapstone.fuseable.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


//Cors문제 해결용 SOP해결위해서 다른 origin을 저장해두는것
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*"); //모든 도메인 허용
        config.addAllowedHeader("*");        //모든 헤더 허용
        config.addExposedHeader("*");
        config.addAllowedMethod("*");        //모든 메소드 허용
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }

}
