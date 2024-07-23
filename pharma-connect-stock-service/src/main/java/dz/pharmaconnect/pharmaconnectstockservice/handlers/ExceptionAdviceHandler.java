package dz.pharmaconnect.pharmaconnectstockservice.handlers;

import dz.pharmaconnect.pharmaconnectstockservice.exceptions.ApiException;
import dz.pharmaconnect.pharmaconnectstockservice.exceptions.BadRequestException;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.responses.ServerErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class ExceptionAdviceHandler {

//    @ExceptionHandler({Exception.class})
//    public ResponseEntity<String> handleUnexpectedError(Exception e) {
//        e.printStackTrace();
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//    }


    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ServerErrorResponse<Object>> handleApiError(ApiException e) {
        return ResponseEntity.status(e.getStatus()).body(new ServerErrorResponse<>("Server Error", e.getData()));
    }


    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ServerErrorResponse<Object>> handleBadRequest(BadRequestException e) {
        return ResponseEntity.status(e.getStatus()).body(new ServerErrorResponse<>("Bad Request", e.getData()));
    }
}
