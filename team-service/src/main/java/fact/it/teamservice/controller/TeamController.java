package fact.it.teamservice.controller;


import fact.it.teamservice.dto.TeamRequest;
import fact.it.teamservice.dto.TeamResponse;
import fact.it.teamservice.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TeamController {

    private final TeamService teamService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<TeamResponse> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/by-id/{teamCode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TeamResponse> getTeamByTeamCode(@PathVariable String teamCode) {
        TeamResponse teamResponse = teamService.getTeamByTeamCode(teamCode);
        if (teamResponse != null) {
            return ResponseEntity.ok(teamResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TeamResponse> createTeam(@RequestBody TeamRequest teamRequest) {
        TeamResponse createdTeam = teamService.createTeam(teamRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeam);
    }

    @PutMapping("/edit/{teamCode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TeamResponse> editTeam(@PathVariable String teamCode, @RequestBody TeamRequest teamRequest) {
        TeamResponse updatedTeam = teamService.editTeam(teamCode, teamRequest);
        if (updatedTeam != null) {
            return ResponseEntity.ok(updatedTeam);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/remove/{teamCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> removeTeam(@PathVariable String teamCode) {
        boolean isRemoved = teamService.removeTeam(teamCode);
        if (isRemoved) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}