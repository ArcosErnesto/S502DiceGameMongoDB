package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
