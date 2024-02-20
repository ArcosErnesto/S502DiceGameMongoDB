package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.impl;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.PlayerEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.PlayerAlreadyExistsException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.PlayerUpdateException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    PlayerRepository playerRepository;
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

    public PlayerEntity playerToDomain(PlayerDTO playerDTO) {
        return new PlayerEntity(playerDTO.getPlayerName(), playerDTO.getSuccessRate());
    }
    public PlayerDTO playerToDTO(PlayerEntity player) {
        return new PlayerDTO(player.getId(), player.getPlayerName(), player.getCreationDate(), player.getSuccessRate());
    }
}
