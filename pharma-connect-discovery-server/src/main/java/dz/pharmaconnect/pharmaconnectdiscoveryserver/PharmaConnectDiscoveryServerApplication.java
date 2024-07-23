package dz.pharmaconnect.pharmaconnectdiscoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class PharmaConnectDiscoveryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PharmaConnectDiscoveryServerApplication.class, args);
    }

}
