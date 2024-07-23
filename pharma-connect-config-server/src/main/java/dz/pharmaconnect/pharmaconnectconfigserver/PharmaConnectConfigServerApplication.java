package dz.pharmaconnect.pharmaconnectconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class PharmaConnectConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PharmaConnectConfigServerApplication.class, args);
    }

}
