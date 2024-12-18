package fact.it.matchservice.controller;

import fact.it.matchservice.dto.MatchRequest;
import fact.it.matchservice.dto.MatchResponse;
import fact.it.matchservice.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/by-id/{matchCode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MatchResponse> getMatchByMatchCode(@PathVariable String matchCode) {
        MatchResponse matchResponse = matchService.getMatchByMatchCode(matchCode);
        if (matchResponse != null) {
            return ResponseEntity.ok(matchResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public String createMatch(@RequestBody MatchRequest matchRequest) {
        MatchResponse createdMatch = matchService.createMatch(matchRequest);
        return ((createdMatch != null) ? "Match " + createdMatch.getHomeTeam().getName() + " - " + createdMatch.getAwayTeam().getName() + " is created successfully" : "Creating match failed");
    }

    @PutMapping("/edit/{matchCode}")
    @ResponseStatus(HttpStatus.OK)
    public String editMatch(@PathVariable String matchCode, @RequestBody MatchRequest matchRequest) {
        MatchResponse updatedMatch = matchService.editMatch(matchCode, matchRequest);
        return ((updatedMatch != null) ? "Match " + updatedMatch.getHomeTeam().getName() + " - " + updatedMatch.getAwayTeam().getName() + " is edited successfully" : "Editing match failed");
    }

    @DeleteMapping("/remove/{matchCode}")
    @ResponseStatus(HttpStatus.OK)
    public String removeMatch(@PathVariable String matchCode) {
        MatchResponse deletedMatch = matchService.removeMatch(matchCode);
        return ((deletedMatch != null) ? "Match " + deletedMatch.getHomeTeam().getName() + " - " + deletedMatch.getAwayTeam().getName() + " is removed successfully" : "Deleting match failed");
    }
}
