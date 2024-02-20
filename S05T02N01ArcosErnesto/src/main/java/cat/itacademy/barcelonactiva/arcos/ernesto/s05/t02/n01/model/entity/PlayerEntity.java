package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "player")
public class PlayerEntity {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("playerName")
    private String playerName;

    @JsonProperty("successRate")
    private Double successRate;

    @JsonProperty("creationDate")
    private Date creationDate;

    public PlayerEntity(String playerName, Double successRate) {
        this.playerName = playerName;
        this.successRate = successRate;
        this.creationDate = new Date();
    }

}
