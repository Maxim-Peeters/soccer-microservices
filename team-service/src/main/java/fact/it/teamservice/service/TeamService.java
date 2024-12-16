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
import java.util.Optional;

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
                    Team.builder().name("FC Barcelona").city("Barcelona").country("Spain").teamCode("b9e4d4b8-3c5a-4b5d-bae3-1f3c74d94c4b").build(),
                    Team.builder().name("Real Madrid").city("Madrid").country("Spain").teamCode("a5a0e8e4-1a2b-4f6c-8e3e-cd1a2f9b28e6").build(),
                    Team.builder().name("Manchester United").city("Manchester").country("England").teamCode("c7d2f312-2b8d-4014-9028-637fe3a49e56").build(),
                    Team.builder().name("Bayern Munich").city("Munich").country("Germany").teamCode("d0934c69-85a3-4638-947c-df4f3c8b7dc9").build(),
                    Team.builder().name("Paris Saint-Germain").city("Paris").country("France").teamCode("e4f8bc7b-b9d8-4c19-a91d-0b31a2c8e9b2").build(),
                    Team.builder().name("Juventus").city("Turin").country("Italy").teamCode("f1a6e5d4-6c7d-4b8f-9a1b-2d3c5e7f9d1e").build(),
                    Team.builder().name("Liverpool FC").city("Liverpool").country("England").teamCode("a7b9c4d6-2e1a-489c-8f9e-3b4d6f7a8c9e").build(),
                    Team.builder().name("Chelsea FC").city("London").country("England").teamCode("b4c6d7e8-1f9a-4c3d-8e1b-2c5a6f7e8b9c").build(),
                    Team.builder().name("Arsenal FC").city("London").country("England").teamCode("c9d8e7f6-3a2b-489c-9e4b-6d7a5f8b1c2d").build(),
                    Team.builder().name("AC Milan").city("Milan").country("Italy").teamCode("d8e7f9a6-1b4d-4c2f-8e3a-9c5d7f1b2e6a").build()
            );

            teamRepository.saveAll(teams);
            System.out.println("10 teams have been saved to the database with UUID team codes.");
        }
    }




    public List<TeamResponse> getAllTeams(){
        List<Team> teams = teamRepository.findAll();
        return teams.stream().map(this::mapToTeamResponse).toList();
    }
    public TeamResponse getTeamByTeamCode(String teamCode) {
        Optional<Team> team = teamRepository.findTeamByTeamCode(teamCode);
        return team.map(this::mapToTeamResponse).orElse(null);
    }

    private TeamResponse mapToTeamResponse(Team team) {
        List<PlayerResponse> players;
        players = webClient.get()
                .uri("http://"+playerServiceBaseurl+"/api/players/team",uriBuilder -> uriBuilder
                        .queryParam("teamCode", team.getTeamCode())
                        .build())
        .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PlayerResponse>>() {
                })
                .block();

        return TeamResponse.builder()
                .teamCode(team.getTeamCode())
                .name(team.getName())
                .city(team.getCity())
                .country(team.getCountry())
                .players(players)
                .build();
    }
}
