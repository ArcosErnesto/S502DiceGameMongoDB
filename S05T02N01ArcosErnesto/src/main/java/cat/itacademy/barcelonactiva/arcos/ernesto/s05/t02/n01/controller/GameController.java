package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.controller;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.GameEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.entity.PlayerEntity;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.GameService;
import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.service.PlayerService;
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

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerDTO>addPlayer(@RequestBody PlayerDTO playerDTO){
        PlayerDTO newPlayer = playerService.addPlayer(playerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPlayer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable String id, @RequestBody PlayerDTO playerDTO) {
        PlayerDTO updatedPlayer = playerService.updatePlayer(id, playerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedPlayer);
    }

    @GetMapping("")
    public ResponseEntity<List<PlayerEntity>> findAll() {
        return ResponseEntity.ok().body(playerService.getAll());
    }

    @PostMapping("/{id}/games")
    public ResponseEntity<GameDTO> play(@PathVariable("id") String id){
        GameDTO newGame = playerService.playGame(id);
        return new ResponseEntity<>(newGame, HttpStatus.OK);
    }

    @GetMapping("/{id}/games")
    public ResponseEntity<List<GameEntity>> getOnePLayerGames(@PathVariable(value = "id") String id){
        List<GameEntity> playerGames = gameService.getOnePlayerGames(id);
        return ResponseEntity.ok(playerGames);
    }

    @DeleteMapping("/{id}/games")
    public ResponseEntity<String>deletePlayerGames(@PathVariable String id){
        String msg = gameService.deletePlayerGames(id);
        playerService.resetSuccessRate(id);
        return ResponseEntity.ok(msg);
    }


    @GetMapping("/ranking")
    public ResponseEntity<List<PlayerDTO>> getRanking(){
        List<PlayerDTO> list = playerService.getRanking();
        return ResponseEntity.ok(list);
    }


    @GetMapping("/ranking/winner")
    public ResponseEntity<PlayerDTO> getWinner(){
        PlayerDTO winner = playerService.getWinner();
        return ResponseEntity.ok(winner);
    }


    @GetMapping("/ranking/loser")
    public ResponseEntity<PlayerDTO> getLoser(){
        PlayerDTO loser = playerService.getLoser();
        return ResponseEntity.ok(loser);
    }
}
