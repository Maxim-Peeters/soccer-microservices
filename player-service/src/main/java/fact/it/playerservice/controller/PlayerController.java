package fact.it.playerservice.controller;

import fact.it.playerservice.dto.PlayerRequest;
import fact.it.playerservice.dto.PlayerResponse;
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

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public String createPlayer(@RequestBody PlayerRequest playerRequest) {
        PlayerResponse createdPlayer = playerService.createPlayer(playerRequest);
        return ((createdPlayer != null) ? "Player " + createdPlayer.getFirstName() + " " + createdPlayer.getLastName()
                + " is created successfully" : "Creating player failed");
    }

    @PutMapping("/edit/{playerCode}")
    @ResponseStatus(HttpStatus.OK)
    public String editPlayer(@PathVariable String playerCode, @RequestBody PlayerRequest playerRequest) {
        PlayerResponse updatedPlayer = playerService.editPlayer(playerCode, playerRequest);
        return ((updatedPlayer != null) ? "Player " + updatedPlayer.getFirstName() + " " + updatedPlayer.getLastName()
                + " is edited successfully" : "Editing player failed");
    }

    @DeleteMapping("/remove/{playerCode}")
    @ResponseStatus(HttpStatus.OK)
    public String removePlayer(@PathVariable String playerCode) {
        PlayerResponse deletedPlayer = playerService.removePlayer(playerCode);
        return ((deletedPlayer != null) ? "Player " + deletedPlayer.getFirstName() + " " + deletedPlayer.getLastName()
                + " is removed successfully" : "Deleting player failed");
    }
}
