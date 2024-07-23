package dz.pharmaconnect.pharmaconnectstockservice;

import dz.pharmaconnect.pharmaconnectstockservice.services.database.StockService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
class PharmaConnectStockServiceApplicationTests {

    @Autowired
    StockService stockManagementService;


    //    @Test
//    void contextLoads() {
//
//    }
//
//

//
//    @Test
//    void syncUpdateFunctional() {
//
//    }


}
