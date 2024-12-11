package fact.it.matchservice.service;

import fact.it.matchservice.dto.MatchResponse;
import fact.it.matchservice.model.Match;
import fact.it.matchservice.repository.MatchRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;

    @PostConstruct
    public void loadData() {
        if (matchRepository.count() <= 0) {
            List<Match> matches = List.of(
                    Match.builder()
                            .homeTeam("FC Barcelona")
                            .awayTeam("Real Madrid")
                            .dateTime(LocalDateTime.of(2023, 12, 15, 20, 0))
                            .location("Camp Nou")
                            .homeTeamScore(2)
                            .awayTeamScore(3)
                            .build(),
                    Match.builder()
                            .homeTeam("Manchester City")
                            .awayTeam("Liverpool")
                            .dateTime(LocalDateTime.of(2023, 12, 16, 18, 30))
                            .location("Etihad Stadium")
                            .homeTeamScore(1)
                            .awayTeamScore(1)
                            .build(),
                    Match.builder()
                            .homeTeam("Bayern Munich")
                            .awayTeam("Borussia Dortmund")
                            .dateTime(LocalDateTime.of(2023, 12, 17, 15, 45))
                            .location("Allianz Arena")
                            .homeTeamScore(4)
                            .awayTeamScore(2)
                            .build(),
                    Match.builder()
                            .homeTeam("Chelsea")
                            .awayTeam("Arsenal")
                            .dateTime(LocalDateTime.of(2023, 12, 18, 16, 0))
                            .location("Stamford Bridge")
                            .homeTeamScore(0)
                            .awayTeamScore(0)
                            .build(),
                    Match.builder()
                            .homeTeam("PSG")
                            .awayTeam("Marseille")
                            .dateTime(LocalDateTime.of(2023, 12, 19, 21, 0))
                            .location("Parc des Princes")
                            .homeTeamScore(3)
                            .awayTeamScore(1)
                            .build()
            );

            matchRepository.saveAll(matches);
            System.out.println("5 matches have been saved to the database.");
        }

    }
    public List<MatchResponse> getAllMatches(){
        List<Match> matches = matchRepository.findAll();
        return matches.stream().map(this::mapToMatchResponse).toList();
    }
    private MatchResponse mapToMatchResponse(Match match) {
        return MatchResponse.builder()
                .id(match.getId())
                .homeTeam(match.getHomeTeam())
                .awayTeam(match.getAwayTeam())
                .dateTime(match.getDateTime())
                .location(match.getLocation())
                .homeTeamScore(match.getHomeTeamScore())
                .awayTeamScore(match.getAwayTeamScore())
                .build();
    }
}
