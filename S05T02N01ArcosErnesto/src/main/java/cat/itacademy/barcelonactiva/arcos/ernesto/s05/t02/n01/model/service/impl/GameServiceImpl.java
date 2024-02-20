package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.impl;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.GameEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.PlayerEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.repository.GameRepository;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.GameService;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.utils.DiceRoll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;
    private DiceRoll diceRoll = new DiceRoll();
    private GameDTO newGame() {
        diceRoll.roll();
        return new GameDTO(diceRoll.getDice1(), diceRoll.getDice2());
    }

    @Override
    public GameDTO addGame(Optional<PlayerEntity> playerEntity) {
        GameDTO newGameDTO = newGame();
        GameEntity gameEntity = gameDTOToEntity(playerEntity,newGameDTO);
        gameRepository.save(gameEntity);
        return newGameDTO;
    }

    @Override
    public List<GameEntity> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public List<GameEntity> getOnePlayerGames(long id) {
        return null;
    }

    @Override
    public String deletePlayerGames(long id) {
        return null;
    }

    @Override
    public GameEntity gameDTOToEntity(Optional<PlayerEntity> playerDTO, GameDTO gameDTO) {
        PlayerEntity player = playerDTO
                .map(dto -> playerRepository.findById(dto.getId()))
                .orElseThrow(() -> new PlayerNotFoundException("No se proporcionó un jugador válido para crear el juego."))
                .orElseThrow(() -> new PlayerNotFoundException("El jugador con el ID proporcionado no existe."));

        return new GameEntity(player, gameDTO.getDice1(), gameDTO.getDice2(), gameDTO.isWin());
    }

    @Override
    public GameDTO gameEntityToDTO(GameEntity gameEntity) {
        return new GameDTO(gameEntity.getDice1(), gameEntity.getDice2(), gameEntity.isWin());
    }
}
