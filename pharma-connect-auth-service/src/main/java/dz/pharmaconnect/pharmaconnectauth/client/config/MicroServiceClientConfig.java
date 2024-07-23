package dz.pharmaconnect.pharmaconnectauth.client.config;

import feign.RequestInterceptor;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class MicroServiceClientConfig {
    @Value("${jwt.server.token}")
    private String accessToken;

    @Bean
    public RequestInterceptor getInterceptor() {
        return requestTemplate -> requestTemplate.header("authorization", accessToken);
    }
}
