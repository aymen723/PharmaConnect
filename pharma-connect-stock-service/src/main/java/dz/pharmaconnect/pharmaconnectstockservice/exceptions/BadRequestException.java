package dz.pharmaconnect.pharmaconnectstockservice.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException {

    protected HttpStatus status = HttpStatus.BAD_REQUEST;


}
