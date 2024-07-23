package dz.pharmaconnect.pharmaconnectgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PharmaConnectGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PharmaConnectGatewayApplication.class, args);
    }

}
