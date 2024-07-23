package dz.pharmaconnect.pharmaconnectstockservice.model.api.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServerErrorResponse<T> {
    private String errorMessage = "internal server error";
    private final boolean isServerError = true;
    private T errorData = null;


}
