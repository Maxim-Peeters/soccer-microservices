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

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<MatchResponse> getAllMatches(){return matchService.getAllMatches();}

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MatchResponse> createMatch(@RequestBody MatchRequest matchRequest) {
        MatchResponse createdMatch = matchService.createMatch(matchRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMatch);
    }

    @PutMapping("/edit/{matchCode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MatchResponse> editMatch(@PathVariable String matchCode, @RequestBody MatchRequest matchRequest) {
        MatchResponse updatedMatch = matchService.editMatch(matchCode, matchRequest);
        if (updatedMatch != null) {
            return ResponseEntity.ok(updatedMatch);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/remove/{matchCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> removeMatch(@PathVariable String matchCode) {
        boolean isRemoved = matchService.removeMatch(matchCode);
        if (isRemoved) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
