package fact.it.teamservice.controller;


import fact.it.teamservice.dto.TeamResponse;
import fact.it.teamservice.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<TeamResponse> getAllTeams(){return teamService.getAllTeams();}

    @GetMapping("/match")
    @ResponseStatus(HttpStatus.OK)
    public List<TeamResponse> getTeamsByMatchCode(@RequestParam String matchCode){
        return teamService.getTeamsByMatchCode(matchCode);
    }
}
