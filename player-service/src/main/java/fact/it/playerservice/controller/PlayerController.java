package fact.it.playerservice.controller;

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
@CrossOrigin(origins = "http://localhost:3000")
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping()
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
}
