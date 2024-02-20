package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions;

public class PlayerAlreadyExistsException extends RuntimeException {
    public PlayerAlreadyExistsException(String message) {
        super(message);
    }
}
