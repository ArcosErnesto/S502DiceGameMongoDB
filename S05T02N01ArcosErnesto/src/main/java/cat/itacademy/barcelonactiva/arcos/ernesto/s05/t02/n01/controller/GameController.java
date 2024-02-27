package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.controller;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.GameEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.PlayerEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.GameService;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diceGame/v1/players")
public class GameController {
    @Autowired
    private PlayerService playerService;
    @Autowired
    GameService gameService;

    @Operation(summary = "Create new player")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerDTO>addPlayer(@RequestBody PlayerDTO playerDTO){
        PlayerDTO newPlayer = playerService.addPlayer(playerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPlayer);
    }

    @Operation(summary = "Update player")
    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable String id, @RequestBody PlayerDTO playerDTO) {
        PlayerDTO updatedPlayer = playerService.updatePlayer(id, playerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedPlayer);
    }

    @Operation(summary = "Get all players")
    @GetMapping("")
    public ResponseEntity<List<PlayerEntity>> findAll() {
        return ResponseEntity.ok().body(playerService.getAll());
    }

    @Operation(summary = "Start new game for player with ID")
    @PostMapping("/{id}/games")
    public ResponseEntity<GameDTO> play(@PathVariable("id") String id){
        GameDTO newGame = playerService.playGame(id);
        return new ResponseEntity<>(newGame, HttpStatus.OK);
    }

    @Operation(summary = "Get player games by ID")
    @GetMapping("/{id}/games")
    public ResponseEntity<List<GameEntity>> getOnePLayerGames(@PathVariable(value = "id") String id){
        List<GameEntity> playerGames = gameService.getOnePlayerGames(id);
        return ResponseEntity.ok(playerGames);
    }

    @Operation(summary = "Delete player game records by ID")
    @DeleteMapping("/{id}/games")
    public ResponseEntity<String>deletePlayerGames(@PathVariable String id){
        String msg = gameService.deletePlayerGames(id);
        playerService.resetSuccessRate(id);
        return ResponseEntity.ok(msg);
    }

    @Operation(summary = "Players ranking")
    @GetMapping("/ranking")
    public ResponseEntity<List<PlayerDTO>> getRanking(){
        List<PlayerDTO> list = playerService.getRanking();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get winner")
    @GetMapping("/ranking/winner")
    public ResponseEntity<PlayerDTO> getWinner(){
        PlayerDTO winner = playerService.getWinner();
        return ResponseEntity.ok(winner);
    }

    @Operation(summary = "Get loser")
    @GetMapping("/ranking/loser")
    public ResponseEntity<PlayerDTO> getLoser(){
        PlayerDTO loser = playerService.getLoser();
        return ResponseEntity.ok(loser);
    }
}
