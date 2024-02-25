package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.GameEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.PlayerEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.PlayerAlreadyExistsException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.repository.GameRepository;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.impl.GameServiceImpl;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.impl.PlayerServiceImpl;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.utils.DiceRoll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceImplTest {
    @Mock
    PlayerRepository playerRepository;
    @Mock
    GameRepository gameRepository;
    @InjectMocks
    PlayerServiceImpl playerService;
    @InjectMocks
    GameServiceImpl gameService;
    @InjectMocks
    DiceRoll diceRoll;
    private PlayerEntity player1;
    private PlayerEntity player2;
    private PlayerDTO playerDTO1;
    private PlayerDTO playerDTO2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        playerService = new PlayerServiceImpl(playerRepository, gameService);
        player1 = PlayerEntity.builder().id("1").playerName("Chiquito").successRate(75d).creationDate(new Date()).build();
        player2 = PlayerEntity.builder().id("2").playerName("P.Tinto").successRate(33.33d).creationDate(new Date()).build();
        playerDTO1 = playerService.playerToDTO(player1);
        playerDTO2 = playerService.playerToDTO(player2);
    }

    @Test
    void save_should_insert_new_superHero() {
        when(playerRepository.existsByPlayerName("Chiquito")).thenReturn(false);
        when(playerRepository.save(any(PlayerEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        playerService.addPlayer(playerDTO1);
        verify(playerRepository, times(1)).save(any(PlayerEntity.class));
        ArgumentCaptor<PlayerEntity> playerEntityArgumentCaptor = ArgumentCaptor.forClass(PlayerEntity.class);
        verify(playerRepository).save(playerEntityArgumentCaptor.capture());
        PlayerEntity savedPlayerEntity = playerEntityArgumentCaptor.getValue();
        assertEquals("Chiquito", savedPlayerEntity.getPlayerName());
    }

    @Test
    void save_should_return_superHero_already_exist() {
        when(playerRepository.existsByPlayerName("Chiquito")).thenReturn(true);
        PlayerAlreadyExistsException playerAlreadyExistsException = assertThrows(PlayerAlreadyExistsException.class,
                () -> playerService.addPlayer(playerDTO1));
        assertEquals("Ya existe un jugador con el nombre: " + playerDTO1.getPlayerName(), playerAlreadyExistsException.getMessage());
        verify(playerRepository, never()).save(any(PlayerEntity.class));
    }

    @Test
    void save_should_update_existing_superHero() {
        when(playerRepository.findById("1")).thenReturn(Optional.of(player1));
        when(playerRepository.save(any(PlayerEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        PlayerDTO playerDTO1 = playerService.playerToDTO(player1);
        playerService.addPlayer(playerDTO1);
        playerDTO1.setPlayerName("Gila");
        PlayerDTO updatedPlayer = playerService.updatePlayer("1", playerDTO1);
        assertEquals("Gila", updatedPlayer.getPlayerName());
    }

    @Test
    void whenUpdatePlayerAlreadyExists_thenPlayerAlreadyExistsExceptionIsThrown() {
        PlayerDTO player = PlayerDTO.builder().id("10L").playerName("Eugenio").successRate(12.5d).creationDate(new Date()).build();
        when(playerRepository.existsByPlayerName(player.getPlayerName())).thenReturn(true);
        PlayerAlreadyExistsException playerAlreadyExistsException = assertThrows(PlayerAlreadyExistsException.class,
                () -> playerService.updatePlayer(player.getId(), player));
        assertEquals("Ya existe un jugador con el nombre: " + player.getPlayerName(), playerAlreadyExistsException.getMessage());
    }

    @Test
    void findAll_should_return_player_list() {
        when(playerRepository.findAll()).thenReturn(Arrays.asList(player1, player2));
        List<PlayerEntity> playersList = playerService.getAll();
        assertEquals(2, playersList.size());
        verify(playerRepository).findAll();
    }

    @Test
    void whenPlayGame_thenSuccess() {
        GameServiceImpl mockGameService = mock(GameServiceImpl.class);
        PlayerEntity player = player1;
        GameDTO gameDTO = new GameDTO(5, 2, true);
        when(playerRepository.findById(player1.getId())).thenReturn(Optional.of(player));
        lenient().when(mockGameService.addGame(any())).thenReturn(gameDTO);
        lenient().when(mockGameService.gameDTOToEntity(any(), any()))
                .thenReturn(new GameEntity(player1, gameDTO.getDice1(), gameDTO.getDice2(), gameDTO.isWin()));
        GameDTO result = playerService.playGame(player1.getId());
        assertNotNull(result);
    }

    @Test
    void whenPlayGamePlayerNotFound_thenExceptionThrown() {
        when(playerRepository.findById("1L")).thenReturn(Optional.empty());
        assertThrows(PlayerNotFoundException.class, () -> playerService.playGame("1L"));
    }

    @Test
    void ranking_should_return_player_list_order_by_successRate() {
        when(playerRepository.findAll()).thenReturn(Arrays.asList(player1, player2));
        List<PlayerDTO> playersList = playerService.getRanking();
        assertEquals(2, playersList.size());
        assertEquals("Chiquito", playersList.get(0).getPlayerName());
        assertEquals("P.Tinto", playersList.get(1).getPlayerName());
    }
}
