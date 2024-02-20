package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service;


import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.GameEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.PlayerEntity;

import java.util.List;
import java.util.Optional;

public interface GameService {
    GameDTO addGame(Optional<PlayerEntity> playerEntity);
    List<GameEntity> getAllGames();
    List<GameEntity> getOnePlayerGames(String id);
    String deletePlayerGames(String id);
    GameEntity gameDTOToEntity(Optional<PlayerEntity> playerDTO, GameDTO gameDTO);
    GameDTO gameEntityToDTO(GameEntity gameEntity);
}