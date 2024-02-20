package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<ExceptionDetails> playerNotFoundException(PlayerNotFoundException exception) {
        ExceptionDetails details = new ExceptionDetails(exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlayerUpdateException.class)
    public ResponseEntity<ExceptionDetails> playerUpdateException(PlayerUpdateException exception) {
        ExceptionDetails details = new ExceptionDetails(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PlayerAlreadyExistsException.class)
    public ResponseEntity<ExceptionDetails> PlayerAlreadyExistsException(PlayerAlreadyExistsException exception) {
        ExceptionDetails details = new ExceptionDetails(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ExceptionDetails> gameNotFoundException(GameNotFoundException exception) {
        ExceptionDetails details = new ExceptionDetails(exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ExceptionDetails> UserAlreadyExistException(UserAlreadyExistException exception) {
        ExceptionDetails details = new ExceptionDetails(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionDetails> BadCredentialsException(BadCredentialsException exception) {
        ExceptionDetails details = new ExceptionDetails(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }
}
