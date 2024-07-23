package dz.pharmaconnect.pharmaconnectstockservice.model.api.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStockRequest {

    private Boolean overridden;

    private Boolean overriddenAvailability;

    @Min(0)
    private Double price;
}
