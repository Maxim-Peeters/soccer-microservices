package fact.it.teamservice.service;

import fact.it.teamservice.dto.TeamResponse;
import fact.it.teamservice.model.Team;
import fact.it.teamservice.repository.TeamRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    @PostConstruct
    public void loadData(){
        if (teamRepository.count() <= 0) {
            List<Team> teams = List.of(
                    Team.builder().name("FC Barcelona").city("Barcelona").country("Spain").build(),
                    Team.builder().name("Real Madrid").city("Madrid").country("Spain").build(),
                    Team.builder().name("Manchester United").city("Manchester").country("England").build(),
                    Team.builder().name("Bayern Munich").city("Munich").country("Germany").build(),
                    Team.builder().name("Paris Saint-Germain").city("Paris").country("France").build(),
                    Team.builder().name("Juventus").city("Turin").country("Italy").build(),
                    Team.builder().name("Liverpool FC").city("Liverpool").country("England").build(),
                    Team.builder().name("Chelsea FC").city("London").country("England").build(),
                    Team.builder().name("Arsenal FC").city("London").country("England").build(),
                    Team.builder().name("AC Milan").city("Milan").country("Italy").build()
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
        return TeamResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .city(team.getCity())
                .country(team.getCountry())
                .build();
    }
}
