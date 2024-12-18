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
    @ResponseStatus(HttpStatus.OK)
    public String createTeam(@RequestBody TeamRequest teamRequest) {
        TeamResponse createdTeam = teamService.createTeam(teamRequest);
        return ((createdTeam != null) ? "Team " + createdTeam.getName() + " is created successfully" : "Creating team failed");
    }

    @PutMapping("/edit/{teamCode}")
    @ResponseStatus(HttpStatus.OK)
    public String editTeam(@PathVariable String teamCode, @RequestBody TeamRequest teamRequest) {
        TeamResponse updatedTeam = teamService.editTeam(teamCode, teamRequest);
        return ((updatedTeam != null) ? "Team " + updatedTeam.getName() + " is edited successfully" : "Editing team failed");
    }

    @DeleteMapping("/remove/{teamCode}")
    @ResponseStatus(HttpStatus.OK)
    public String removeTeam(@PathVariable String teamCode) {
        TeamResponse deletedTeam = teamService.removeTeam(teamCode);
        return ((deletedTeam != null) ? "Team " + deletedTeam.getName() + " is removed successfully" : "Deleting team failed");
    }
}