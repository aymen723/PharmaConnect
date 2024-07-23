package dz.pharmaconnect.pharmaconnectauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients

public class PharmaConnectAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(PharmaConnectAuthApplication.class, args);
    }

}
