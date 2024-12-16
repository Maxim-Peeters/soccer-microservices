package fact.it.matchservice.controller;

import fact.it.matchservice.dto.MatchResponse;
import fact.it.matchservice.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<MatchResponse> getAllMatches(){return matchService.getAllMatches();}

}
