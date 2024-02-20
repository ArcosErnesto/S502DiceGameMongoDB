package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.PlayerEntity;

import java.util.List;

public interface PlayerService {
    PlayerDTO addPlayer(PlayerDTO player);
    PlayerDTO updatePlayer(String id, PlayerDTO player);
    List<PlayerEntity>getAll();
    GameDTO playGame(String id);
}
