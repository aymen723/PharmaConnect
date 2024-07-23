package dz.pharmaconnect.pharmaconnectstockservice.model.api.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SyncStockRequest {

    private Set<Integer> availableProds;
    private boolean propagate = false;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SyncData {

        @NotNull
        private Integer productId;
        @NotNull
        private boolean available;

    }
}
