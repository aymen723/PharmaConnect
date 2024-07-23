package dz.pharmaconnect.pharmaconnectstockservice.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class ApiException extends RuntimeException {
    protected Object data;
    protected HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;


}
