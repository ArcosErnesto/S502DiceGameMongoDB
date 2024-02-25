package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.GameEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.PlayerEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.repository.GameRepository;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.impl.GameServiceImpl;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.utils.DiceRoll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplTest {
    @Mock
    PlayerRepository playerRepository;
    @Mock
    GameRepository gameRepository;
    @Mock
    DiceRoll diceRoll;
    @InjectMocks
    GameServiceImpl gameService;
    private PlayerEntity player1;

    @BeforeEach
    void setUp() {
        player1 = PlayerEntity.builder().id("1L").playerName("Chiquito").successRate(75d).creationDate(new Date()).build();
    }

    @Test
    void newGame_should_return_newGameDTO() {
        GameDTO result = gameService.newGame();
        assertNotNull(result);
        assertTrue(result.getDice1() >= 1 && result.getDice1() <= 6);
        assertTrue(result.getDice2() >= 1 && result.getDice2() <= 6);
    }

    @Test
    void addGame_should_return_newGameDTO_and_save_gameEntity() {
        Optional<PlayerEntity> optionalPlayer = Optional.ofNullable(player1);
        when(playerRepository.findById("1L")).thenReturn(Optional.of(player1));
        GameDTO newGame = gameService.addGame(optionalPlayer);
        assertNotNull(newGame);
        assertTrue(newGame.getDice1() >= 1 && newGame.getDice1() <= 6);
        assertTrue(newGame.getDice2() >= 1 && newGame.getDice2() <= 6);
        verify(gameRepository, times(1)).save(argThat(gameEntity -> gameEntity.getPlayerEntity().equals(player1)));
    }

    @Test
    void getOnePlayerGames_should_return_list_of_games_for_existing_player() {
        when(playerRepository.findById("1L")).thenReturn(Optional.of(player1));
        GameEntity game1 = GameEntity.builder().playerEntity(player1).dice1(3).dice2(4).build();
        GameEntity game2 = GameEntity.builder().playerEntity(player1).dice1(1).dice2(6).build();
        List<GameEntity> games = Arrays.asList(game1, game2);
        when(gameRepository.findAll()).thenReturn(games);
        List<GameEntity> result = gameService.getOnePlayerGames(player1.getId());
        assertEquals(2, result.size());
        assertEquals(player1.getId(), result.get(0).getPlayerEntity().getId());
        assertEquals(player1.getId(), result.get(1).getPlayerEntity().getId());
    }

    @Test
    void getOnePlayerGames_should_throw_exception_for_nonexistent_player() {
        when(playerRepository.findById("10L")).thenReturn(Optional.empty());
        assertThrows(PlayerNotFoundException.class, () -> gameService.getOnePlayerGames("10L"));
    }

    @Test
    void deletePlayerGames_should_delete_games_for_existing_player() {
        when(playerRepository.findById("1L")).thenReturn(Optional.of(player1));
        GameEntity game1 = GameEntity.builder().playerEntity(player1).dice1(3).dice2(4).build();
        GameEntity game2 = GameEntity.builder().playerEntity(player1).dice1(1).dice2(6).build();
        List<GameEntity> games = Arrays.asList(game1, game2);
        when(gameRepository.findAll()).thenReturn(games);
        String result = gameService.deletePlayerGames(player1.getId());
        assertEquals("Borradas con éxito las partidas del jugador Chiquito", result);
    }

    @Test
    void deletePlayerGames_should_throw_exception_for_nonexistent_player(){
        when(playerRepository.findById("10L")).thenReturn(Optional.empty());
        assertThrows(PlayerNotFoundException.class, () -> gameService.deletePlayerGames("10L"));
    }
}
