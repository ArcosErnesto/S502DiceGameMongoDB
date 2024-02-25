package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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
    @Indexed(unique = true)
    private String playerName;

    @JsonProperty("successRate")
    private Double successRate;

    @NotNull
    @JsonProperty("creationDate")
    private Date creationDate;

    public PlayerEntity(String playerName, Double successRate) {
        this.playerName = playerName;
        this.successRate = successRate;
        this.creationDate = new Date();
    }

}
