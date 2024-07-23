package dz.pharmaconnect.pharmaconnectstockservice.model.api.filters;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class OrderSearchFilter {

    private Long accountId;
    private Integer pharmacyId;
    private Long pharmacyAccountId;
    private UUID orderSecret;


}
