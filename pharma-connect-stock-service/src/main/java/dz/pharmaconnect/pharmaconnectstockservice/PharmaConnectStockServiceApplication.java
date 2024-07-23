package dz.pharmaconnect.pharmaconnectstockservice;

import dz.pharmaconnect.pharmaconnectstockservice.services.database.DummyDataFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@RequiredArgsConstructor
@Slf4j
public class PharmaConnectStockServiceApplication implements CommandLineRunner {
    @Value("${application.database.insert-dev-values:false}")
    private boolean insertDevValues;
    @Value("${spring.jpa.hibernate.ddl-auto:none}")
    private String ddlAuto;
    private final DummyDataFactory dummyDataFactory;

    public static void main(String[] args) {
        SpringApplication.run(PharmaConnectStockServiceApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        log.info("the values of ddl-auto :" + ddlAuto);

        if (ddlAuto.equals("create-drop") || ddlAuto.equals("create") && insertDevValues) {
            log.info("inserting dev database rows");
            dummyDataFactory.createDummyData();
        }
    }
}
