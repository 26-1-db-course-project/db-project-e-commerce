package db.project.ecommerce.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS 설정.
 *
 * 프론트엔드(Vercel)와 백엔드(AWS)가 서로 다른 출처(origin)이므로,
 * 허용할 프론트 주소를 명시해야 브라우저가 요청을 막지 않는다.
 *
 * 허용 출처는 application 설정의 app.cors.allowed-origins 로 외부화한다.
 *  - 개발 기본값: http://localhost:5173 (Vite 개발 서버)
 *  - 운영(AWS)에서는 환경변수로 덮어쓴다. 예)
 *      APP_CORS_ALLOWED_ORIGINS=https://내앱.vercel.app,https://*.vercel.app
 *    (쉼표로 여러 개, 와일드카드 패턴 가능 → Vercel 미리보기 배포까지 허용 가능)
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${app.cors.allowed-origins:http://localhost:5173}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // allowedOriginPatterns: 와일드카드(*.vercel.app) 허용 + allowCredentials 와 함께 사용 가능
                .allowedOriginPatterns(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
