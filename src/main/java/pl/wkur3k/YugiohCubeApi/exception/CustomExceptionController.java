package pl.wkur3k.YugiohCubeApi.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class CustomExceptionController {
    @ExceptionHandler(value = InvalidCredentialsException.class)
    public ResponseEntity<Object> exception(InvalidCredentialsException exception){
        return new ResponseEntity<>("Invalid username or password.", HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(value = NotOwnerOfCubeException.class)
    public ResponseEntity<Object> exception(NotOwnerOfCubeException exception){
        return new ResponseEntity<>("User is not owner of this cube.", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = CubeNotFoundException.class)
    public ResponseEntity<Object> exception(CubeNotFoundException exception){
        return new ResponseEntity<>("Cube with this id couldn't be found.", HttpStatus.BAD_REQUEST);
    }
}
