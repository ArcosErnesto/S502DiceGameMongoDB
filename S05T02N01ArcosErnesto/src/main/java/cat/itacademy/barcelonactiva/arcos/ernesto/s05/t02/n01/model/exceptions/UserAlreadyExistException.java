package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String msg) {
        super(msg);
    }
}