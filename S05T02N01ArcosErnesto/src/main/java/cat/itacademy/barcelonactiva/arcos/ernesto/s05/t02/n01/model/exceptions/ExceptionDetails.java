package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionDetails {
    private String message;
    private int description;
}