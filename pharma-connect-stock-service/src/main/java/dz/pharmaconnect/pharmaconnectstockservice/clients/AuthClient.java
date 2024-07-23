package dz.pharmaconnect.pharmaconnectstockservice.clients;

import dz.pharmaconnect.pharmaconnectstockservice.config.client.ClientConfiguration;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.clients.auth.AccountDto;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.clients.auth.requests.CreatePharmacyAccountRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "PHARMA-CONNECT-AUTH", configuration = ClientConfiguration.class)
public interface AuthClient {

    @GetMapping("/api/v1/accounts/{id}")
    AccountDto getAccount(@PathVariable Long id);

    @GetMapping("/api/v1/accounts")
    List<AccountDto> getAccounts();

    @PostMapping("/api/v1/accounts/pharmacy")
    AccountDto createPharmacyAccount(@RequestBody CreatePharmacyAccountRequest request);


}
