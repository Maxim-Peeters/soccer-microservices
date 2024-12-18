package fact.it.matchservice.service;

import fact.it.matchservice.dto.MatchRequest;
import fact.it.matchservice.dto.MatchResponse;
import fact.it.matchservice.dto.TeamResponse;
import fact.it.matchservice.model.Match;
import fact.it.matchservice.repository.MatchRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final WebClient webClient;
    @Value("${teamservice.baseurl}")
    private String teamServiceBaseUrl;

    @PostConstruct
    public void loadData() {
        if (matchRepository.count() <= 0) {
            List<Match> matches = List.of(
                    Match.builder()
                            .matchCode(UUID.randomUUID().toString())
                            .homeTeamCode("b9e4d4b8-3c5a-4b5d-bae3-1f3c74d94c4b") // FC Barcelona
                            .awayTeamCode("a5a0e8e4-1a2b-4f6c-8e3e-cd1a2f9b28e6") // Real Madrid
                            .dateTime(LocalDateTime.of(2023, 12, 15, 20, 0))
                            .location("Camp Nou")
                            .homeTeamScore(2)
                            .awayTeamScore(3)
                            .build(),
                    Match.builder()
                            .matchCode(UUID.randomUUID().toString())
                            .homeTeamCode("c7d2f312-2b8d-4014-9028-637fe3a49e56") // Manchester United (changed name, code stays same)
                            .awayTeamCode("a7b9c4d6-2e1a-489c-8f9e-3b4d6f7a8c9e") // Liverpool FC
                            .dateTime(LocalDateTime.of(2023, 12, 16, 18, 30))
                            .location("Etihad Stadium")
                            .homeTeamScore(1)
                            .awayTeamScore(1)
                            .build(),
                    Match.builder()
                            .matchCode(UUID.randomUUID().toString())
                            .homeTeamCode("d0934c69-85a3-4638-947c-df4f3c8b7dc9") // Bayern Munich
                            .awayTeamCode("f1a6e5d4-6c7d-4b8f-9a1b-2d3c5e7f9d1e") // Juventus
                            .dateTime(LocalDateTime.of(2023, 12, 17, 15, 45))
                            .location("Allianz Arena")
                            .homeTeamScore(4)
                            .awayTeamScore(2)
                            .build(),
                    Match.builder()
                            .matchCode(UUID.randomUUID().toString())
                            .homeTeamCode("b4c6d7e8-1f9a-4c3d-8e1b-2c5a6f7e8b9c") // Chelsea FC
                            .awayTeamCode("c9d8e7f6-3a2b-489c-9e4b-6d7a5f8b1c2d") // Arsenal FC
                            .dateTime(LocalDateTime.of(2023, 12, 18, 16, 0))
                            .location("Stamford Bridge")
                            .homeTeamScore(0)
                            .awayTeamScore(0)
                            .build(),
                    Match.builder()
                            .matchCode(UUID.randomUUID().toString())
                            .homeTeamCode("e4f8bc7b-b9d8-4c19-a91d-0b31a2c8e9b2") // PSG
                            .awayTeamCode("d8e7f9a6-1b4d-4c2f-8e3a-9c5d7f1b2e6a") // AC Milan
                            .dateTime(LocalDateTime.of(2023, 12, 19, 21, 0))
                            .location("Parc des Princes")
                            .homeTeamScore(3)
                            .awayTeamScore(1)
                            .build()
            );

            matchRepository.saveAll(matches);
            System.out.println("5 matches have been saved to the database with team codes.");
        }
    }

    public List<MatchResponse> getAllMatches(){
        List<Match> matches = matchRepository.findAll();
        return matches.stream().map(this::mapToMatchResponse).toList();
    }

    public MatchResponse getMatchByMatchCode(String matchCode) {
        Optional<Match> match = matchRepository.findMatchByMatchCode(matchCode);
        return match.map(this::mapToMatchResponse).orElse(null);
    }

    public MatchResponse createMatch(MatchRequest matchRequest) {
        Match match = Match.builder()
                .matchCode(UUID.randomUUID().toString())
                .homeTeamCode(matchRequest.getHomeTeamCode())
                .awayTeamCode(matchRequest.getAwayTeamCode())
                .dateTime(matchRequest.getDateTime())
                .location(matchRequest.getLocation())
                .homeTeamScore(matchRequest.getHomeTeamScore())
                .awayTeamScore(matchRequest.getAwayTeamScore())
                .build();

        Match savedMatch = matchRepository.save(match);
        return mapToMatchResponse(savedMatch);
    }

    // Edit an existing match
    public MatchResponse editMatch(String matchCode, MatchRequest matchRequest) {
        Match existingMatch = matchRepository.findMatchByMatchCode(matchCode)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        updateIfNotNull(matchRequest.getHomeTeamCode(), existingMatch::setHomeTeamCode);
        updateIfNotNull(matchRequest.getAwayTeamCode(), existingMatch::setAwayTeamCode);
        updateIfNotNull(matchRequest.getDateTime(), existingMatch::setDateTime);
        updateIfNotNull(matchRequest.getLocation(), existingMatch::setLocation);
        updateIfNotNull(matchRequest.getHomeTeamScore(), existingMatch::setHomeTeamScore);
        updateIfNotNull(matchRequest.getAwayTeamScore(), existingMatch::setAwayTeamScore);

        Match updatedMatch = matchRepository.save(existingMatch);
        return mapToMatchResponse(updatedMatch);
    }

    private <T> void updateIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }

    public MatchResponse removeMatch(String matchCode) {
        Match deletedMatch = matchRepository.findMatchByMatchCode(matchCode)
                .orElseThrow(() -> new RuntimeException("Match not found with code: " + matchCode));

        matchRepository.delete(deletedMatch);
        return mapToMatchResponse(deletedMatch);
    }

    private TeamResponse getTeamByCode(String teamCode) {
        return webClient.get()
                .uri("http://" + teamServiceBaseUrl + "/api/teams/by-id/" + teamCode)
                .retrieve()
                .bodyToMono(TeamResponse.class)
                .block();
    }
    private MatchResponse mapToMatchResponse(Match match) {
        return MatchResponse.builder()
                .matchCode(match.getMatchCode())
                .homeTeam(getTeamByCode(match.getHomeTeamCode()))
                .awayTeam(getTeamByCode(match.getAwayTeamCode()))
                .dateTime(match.getDateTime())
                .location(match.getLocation())
                .homeTeamScore(match.getHomeTeamScore())
                .awayTeamScore(match.getAwayTeamScore())
                .build();
    }
}
