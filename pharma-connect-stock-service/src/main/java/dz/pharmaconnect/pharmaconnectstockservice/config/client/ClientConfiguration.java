package dz.pharmaconnect.pharmaconnectstockservice.config.client;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class ClientConfiguration {
    @Value("${application.client.authToken}")
    private String token;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Retrieve the JWT token from a secure source (e.g., an authentication service)

            requestTemplate.header("Authorization", "Bearer " + token);
        };
    }
}
