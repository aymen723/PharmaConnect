package dz.pharmaconnect.delivery.client;

import dz.pharmaconnect.delivery.client.config.MicroServiceClientConfig;
import dz.pharmaconnect.delivery.model.api.client.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PHARMA-CONNECT-AUTH" , configuration = MicroServiceClientConfig.class)
public interface AuthClient {
    @GetMapping("/api/v1/accounts/{accountId}")
    public AccountDto getAccountById(@PathVariable Long accountId);
}
