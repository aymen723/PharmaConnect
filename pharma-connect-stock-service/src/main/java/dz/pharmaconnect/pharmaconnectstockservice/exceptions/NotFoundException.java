package dz.pharmaconnect.pharmaconnectstockservice.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;


public class NotFoundException extends ApiException {
    protected HttpStatus status = HttpStatus.NOT_FOUND;

}
