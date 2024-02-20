package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "games")
public class GameEntity {
    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("playerEntity")
    private PlayerEntity playerEntity;
    @JsonProperty("dice1")
    private int dice1;
    @JsonProperty("dice2")
    private int dice2;
    @JsonProperty("win")
    private boolean win;

    public GameEntity(PlayerEntity playerEntity, int dice1, int dice2, boolean win) {
        this.playerEntity = playerEntity;
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.win = win;
    }
}
