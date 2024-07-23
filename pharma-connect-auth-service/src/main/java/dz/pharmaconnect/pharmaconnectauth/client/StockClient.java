package dz.pharmaconnect.pharmaconnectauth.client;

import dz.pharmaconnect.pharmaconnectauth.client.config.MicroServiceClientConfig;
import dz.pharmaconnect.pharmaconnectauth.model.api.client.Pharmacy;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "PHARMA-CONNECT-STOCK-SERVICE" , configuration = MicroServiceClientConfig.class)
public interface StockClient {


    @GetMapping("/api/v1/pharmacies/{id}")
    Pharmacy getPharmacy(@PathVariable Integer id);

    @GetMapping("/api/v1/pharmacies/{id}")
    Pharmacy getPharmacyByAccountId(@PathVariable Long id, @RequestParam boolean isAccountId);
}
