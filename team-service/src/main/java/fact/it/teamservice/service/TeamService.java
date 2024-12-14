package fact.it.teamservice.service;

import fact.it.teamservice.dto.PlayerResponse;
import fact.it.teamservice.dto.TeamResponse;
import fact.it.teamservice.model.Team;
import fact.it.teamservice.repository.TeamRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final WebClient webClient;
    private final TeamRepository teamRepository;

    @Value("${playerservice.baseurl}")
    private String playerServiceBaseurl;

    @PostConstruct
    public void loadData() {
        if (teamRepository.count() <= 0) {
            List<Team> teams = List.of(
                    Team.builder().name("FC Barcelona").city("Barcelona").country("Spain").teamCode("FCB-ESP-001").build(),
                    Team.builder().name("Real Madrid").city("Madrid").country("Spain").teamCode("RM-ESP-002").build(),
                    Team.builder().name("Manchester United").city("Manchester").country("England").teamCode("MUFC-ENG-003").build(),
                    Team.builder().name("Bayern Munich").city("Munich").country("Germany").teamCode("BAYERN-DEU-004").build(),
                    Team.builder().name("Paris Saint-Germain").city("Paris").country("France").teamCode("PSG-FRA-005").build(),
                    Team.builder().name("Juventus").city("Turin").country("Italy").teamCode("JUVE-ITA-006").build(),
                    Team.builder().name("Liverpool FC").city("Liverpool").country("England").teamCode("LIV-ENG-007").build(),
                    Team.builder().name("Chelsea FC").city("London").country("England").teamCode("CHE-ENG-008").build(),
                    Team.builder().name("Arsenal FC").city("London").country("England").teamCode("ARS-ENG-009").build(),
                    Team.builder().name("AC Milan").city("Milan").country("Italy").teamCode("ACM-ITA-010").build()
            );

            teamRepository.saveAll(teams);
            System.out.println("10 teams have been saved to the database.");
        }
    }



    public List<TeamResponse> getAllTeams(){
        List<Team> events = teamRepository.findAll();
        return events.stream().map(this::mapToTeamResponse).toList();
    }

    private TeamResponse mapToTeamResponse(Team team) {
        List<PlayerResponse> players;
        players = webClient.get()
                .uri("http://"+playerServiceBaseurl+"/api/players/team",uriBuilder -> uriBuilder
                        .queryParam("teamCode", team.getId())
                        .build())
        .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PlayerResponse>>() {
                })
                .block();

        return TeamResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .city(team.getCity())
                .country(team.getCountry())
                .players(players)
                .build();
    }
}
