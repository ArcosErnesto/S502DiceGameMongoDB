package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.repository;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.GameEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.PlayerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<GameEntity, String> {
}
