package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class PlayerDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    private String playerName;
    private Date creationDate;
    private Double successRate;

    public PlayerDTO(String id, String playerName, Date creationDate, Double successRate) {
        this.id = id;
        this.playerName = playerName != null ? playerName : "ANONYMOUS";
        this.creationDate = creationDate;
        this.successRate = successRate;
    }
}
