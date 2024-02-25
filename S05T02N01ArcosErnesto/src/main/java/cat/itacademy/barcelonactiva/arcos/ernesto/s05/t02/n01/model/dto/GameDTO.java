package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class GameDTO {
    private int dice1;
    private int dice2;
    private final int WINNING_SUM = 7;
    private boolean win;

    public GameDTO(int dice1, int dice2) {
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.win = (dice1 + dice2 == WINNING_SUM);
    }

    public GameDTO(int dice1, int dice2, boolean win) {
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.win = (dice1 + dice2 == WINNING_SUM);
    }
}