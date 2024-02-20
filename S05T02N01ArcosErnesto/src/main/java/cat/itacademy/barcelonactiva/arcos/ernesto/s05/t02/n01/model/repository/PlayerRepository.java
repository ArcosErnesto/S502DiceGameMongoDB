package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.repository;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.PlayerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlayerRepository extends MongoRepository<PlayerEntity, String> {
    boolean existsByPlayerName(String playerName);

}
