package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.repository;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.PlayerEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class PlayerRepositoryTest {
    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void findAll_should_return_player_list() {
        List<PlayerEntity> playersList = playerRepository.findAll();
        assertEquals(playersList.size(), 6);
    }

    @Test
    void findById_should_return_superHero() {
        Optional<PlayerEntity> player = playerRepository.findById("65db172333f9915a7829dbf0");
        assertTrue(player.isPresent());
    }

    @Test
    void findById_no_should_return_superHero() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            playerRepository.findById("65d5c0af1a54634ee74a5a28").get();
        });
        assertNotNull(exception);
        assertEquals(exception.getClass(), NoSuchElementException.class);
        assertEquals(exception.getMessage(), "No value present");
    }

    @Test
    void existsByPlayerName_should_return_true() {
        boolean exists = playerRepository.existsByPlayerName("Chiquito");
        assertTrue(exists);
    }

    @Test
    void existsByPlayerName_should_return_false() {
        boolean exists = playerRepository.existsByPlayerName("Mairena");
        assertFalse(exists);
    }

    @Test
    void save_should_insert_new_superHero() {
        PlayerEntity newPlayer = PlayerEntity.builder()
                .playerName("Eugenio")
                .creationDate(new Date())
                .build();

        PlayerEntity returnedPlayer = playerRepository.save(newPlayer);
        assertNotNull(returnedPlayer);
        assertEquals(newPlayer.getPlayerName(), returnedPlayer.getPlayerName());
    }

    @Test
    void save_should_update_existing_player(){
        Optional<PlayerEntity> existingPlayer =playerRepository.findById("65db172333f9915a7829dbf0");
        assertTrue(existingPlayer.isPresent());
        existingPlayer.get().setPlayerName("Arévalo");
        PlayerEntity updatedPlayer = playerRepository.save(existingPlayer.get());
        assertNotNull(updatedPlayer);
        assertEquals("Arévalo", updatedPlayer.getPlayerName());

    }
}
