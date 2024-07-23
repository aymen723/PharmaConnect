package dz.pharmaconnect.delivery.client.config;


import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class MicroServiceClientConfig {
    @Value("${application.jwt.server.token}")
   private String accessToken;

    @Bean
    public RequestInterceptor getInterceptor() {
        return requestTemplate -> requestTemplate.header("authorization" , "Bearer " +  accessToken);
    }
}
