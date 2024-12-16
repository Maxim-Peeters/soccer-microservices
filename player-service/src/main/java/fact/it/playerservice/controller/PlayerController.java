package fact.it.playerservice.controller;

import fact.it.playerservice.dto.PlayerRequest;
import fact.it.playerservice.dto.PlayerResponse;
import fact.it.playerservice.model.Player;
import fact.it.playerservice.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerResponse> getAllPlayers() {return playerService.getAllPlayers();}

    @GetMapping("/team")
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerResponse> getPlayersByTeamCode(@RequestParam String teamCode){
        return playerService.getPlayersByTeamCode(teamCode);
    }

    @GetMapping("/by-id/{playerCode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PlayerResponse> getPlayerByPlayerCode(@PathVariable String playerCode) {
        PlayerResponse playerResponse = playerService.getPlayerByPlayerCode(playerCode);
        if (playerResponse != null) {
            return ResponseEntity.ok(playerResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PlayerResponse> createPlayer(@RequestBody PlayerRequest playerRequest) {
        PlayerResponse createdPlayer = playerService.createPlayer(playerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlayer);
    }

    @PutMapping("/edit/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PlayerResponse> editPlayer(@PathVariable String playerId, @RequestBody PlayerRequest playerRequest) {
        PlayerResponse updatedPlayer = playerService.editPlayer(playerId, playerRequest);
        if (updatedPlayer != null) {
            return ResponseEntity.ok(updatedPlayer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/remove/{playerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> removePlayer(@PathVariable String playerId) {
        boolean isRemoved = playerService.removePlayer(playerId);
        if (isRemoved) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
