package dz.pharmaconnect.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class PharmaConnectDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(PharmaConnectDeliveryApplication.class, args);
    }

}
