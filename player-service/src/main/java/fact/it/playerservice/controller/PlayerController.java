package fact.it.playerservice.controller;

import fact.it.playerservice.dto.PlayerResponse;
import fact.it.playerservice.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
}
