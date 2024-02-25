package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.impl;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.GameEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.PlayerEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.GameNotFoundException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.PlayerAlreadyExistsException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.PlayerUpdateException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.GameService;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.PlayerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    private GameService gameService;
    @Override
    public PlayerDTO addPlayer(PlayerDTO playerDTO) {
        if (playerDTO.getPlayerName() != null && playerRepository.existsByPlayerName(playerDTO.getPlayerName())) {
            throw new PlayerAlreadyExistsException("Ya existe un jugador con el nombre: " + playerDTO.getPlayerName());
        }
        PlayerEntity playerEntity = playerToDomain(playerDTO);
        playerEntity = playerRepository.save(playerEntity);
        return playerToDTO(playerEntity);
    }

    @Override
    public List<PlayerEntity> getAll() {
        return playerRepository.findAll();
    }

    @Override
    public PlayerDTO updatePlayer(String id, PlayerDTO playerDTO) {
        if (playerDTO.getPlayerName() != null && playerRepository.existsByPlayerName(playerDTO.getPlayerName())) {
            throw new PlayerAlreadyExistsException("Ya existe un jugador con el nombre: " + playerDTO.getPlayerName());
        }
        try {
            Optional<PlayerEntity> updatedPlayer = playerRepository.findById(id);

            if (updatedPlayer.isPresent()) {
                PlayerEntity playerDb = updatedPlayer.get();
                playerDb.setPlayerName(playerDTO.getPlayerName());
                playerRepository.save(playerDb);
                return playerToDTO(playerDb);
            } else {
                throw new PlayerNotFoundException("Jugador no encontrado con el ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new PlayerUpdateException("Error al actualizar el jugador con ID: " + id, e);
        }
    }

    @Override
    public GameDTO playGame(String id){
        Optional<PlayerEntity> updatedPlayer = playerRepository.findById(id);
        GameDTO gameDTO = gameService.addGame(updatedPlayer);
        try {
            if (updatedPlayer.isPresent()) {
                updatedPlayer.get();
                PlayerEntity playerDb;
                playerDb = updateSuccessRate(updatedPlayer, gameDTO);
                playerRepository.save(playerDb);
                return gameDTO;
            } else {
                throw new PlayerNotFoundException("Jugador no encontrado con el ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new PlayerUpdateException("Error al actualizar el jugador con ID: " + id, e);
        }
    }

    private PlayerEntity updateSuccessRate(Optional<PlayerEntity> playerOptional, GameDTO gameDTO) {
        PlayerEntity player = playerOptional.orElseThrow(() -> new PlayerNotFoundException("No se proporcionó un jugador válido para actualizar la tasa de éxito."));

        Double successRate = player.getSuccessRate();
        double winGame = gameDTO.isWin() ? 1.0 : 0.0;

        if (successRate == null) {
            successRate = winGame * 100;
        } else {
            List<GameEntity> games = gameService.getAllGames();
            List<GameEntity> playerGames = games.stream()
                    .filter(game -> game.getPlayerEntity().getId().equalsIgnoreCase(player.getId()))
                    .toList();
            double gamesPlayed = playerGames.size();
            List<GameEntity> winGames = playerGames.stream().filter(GameEntity::isWin).toList();
            double winedGames = winGames.size();

            if (gamesPlayed > 0) {
                successRate = winedGames / gamesPlayed * 100;
            } else {
                successRate = winGame * 100;
            }
        }
        player.setSuccessRate(successRate);
        return player;
    }

    public void resetSuccessRate(String id) {
        Optional<PlayerEntity> playerOptional = playerRepository.findById(id);
        if (playerOptional.isEmpty()) {
            throw new PlayerNotFoundException("Jugador no encontrado con id " + id);
        } else {
            PlayerEntity player = playerOptional.get();
            player.setSuccessRate(null);
            playerRepository.save(player);
        }
    }

    @Override
    public List<PlayerDTO> getRanking() {
        List<PlayerDTO> ranking = playerRepository.findAll().stream()
                .map(this::playerToDTO)
                .filter(playerDTO -> playerDTO.getSuccessRate() != null)
                .sorted(Comparator.comparing(PlayerDTO::getSuccessRate).reversed())
                .collect(Collectors.toList());
        if (ranking.isEmpty()) {
            throw new GameNotFoundException("No hay partidas registradas.");
        }
        return ranking;
    }

    @Override
    public PlayerDTO getWinner() {
        List<PlayerDTO> ranking = getRanking();
        return ranking.get(0);
    }

    @Override
    public PlayerDTO getLoser() {
        List<PlayerDTO> ranking = getRanking();
        return ranking.get(ranking.size() - 1);
    }

    public PlayerEntity playerToDomain(PlayerDTO playerDTO) {
        return new PlayerEntity(playerDTO.getPlayerName(), playerDTO.getSuccessRate());
    }
    public PlayerDTO playerToDTO(PlayerEntity player) {
        return new PlayerDTO(player.getId(), player.getPlayerName(), player.getCreationDate(), player.getSuccessRate());
    }
}