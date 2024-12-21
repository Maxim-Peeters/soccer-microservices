package fact.it.matchservice;


import fact.it.matchservice.dto.MatchRequest;
import fact.it.matchservice.dto.MatchResponse;
import fact.it.matchservice.dto.TeamResponse;
import fact.it.matchservice.model.Match;
import fact.it.matchservice.repository.MatchRepository;
import fact.it.matchservice.service.MatchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceUnitTests {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchService matchService;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private Match match1;
    private Match match2;
    private MatchRequest matchRequest;
    private TeamResponse team1;
    private TeamResponse team2;
    private TeamResponse team3;
    private TeamResponse team4;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(matchService, "teamServiceBaseUrl", "localhost:8081");

        match1 = Match.builder()
                .matchCode("match1-code")
                .homeTeamCode("team1-code")
                .awayTeamCode("team2-code")
                .dateTime(LocalDateTime.of(2025, 12, 15, 20, 0))
                .location("Camp Nou")
                .homeTeamScore(2)
                .awayTeamScore(3)
                .build();

        match2 = Match.builder()
                .matchCode("match2-code")
                .homeTeamCode("team3-code")
                .awayTeamCode("team4-code")
                .dateTime(LocalDateTime.of(2025, 12, 16, 18, 30))
                .location("Etihad Stadium")
                .homeTeamScore(1)
                .awayTeamScore(1)
                .build();

        matchRequest = MatchRequest.builder()
                .homeTeamCode("team1-code")
                .awayTeamCode("team2-code")
                .dateTime(LocalDateTime.of(2025, 12, 20, 19, 0))
                .location("Anfield")
                .homeTeamScore(2)
                .awayTeamScore(2)
                .build();

        team1 = TeamResponse.builder()
                .teamCode("team1-code")
                .name("FC Barcelona")
                .build();

        team2 = TeamResponse.builder()
                .teamCode("team2-code")
                .name("Real Madrid")
                .build();

        team3 = TeamResponse.builder()
                .teamCode("team3-code")
                .name("Manchester United")
                .build();

        team4 = TeamResponse.builder()
                .teamCode("team4-code")
                .name("Liverpool FC")
                .build();
    }

    @Test
    void testGetAllMatches() {
        // Arrange
        Mockito.when(matchRepository.findAll()).thenReturn(List.of(match1, match2));

        Mockito.when(webClient.get()).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.uri(Mockito.anyString())).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        Mockito.when(responseSpec.bodyToMono(TeamResponse.class))
                .thenReturn(Mono.just(team1), Mono.just(team2), Mono.just(team3), Mono.just(team4));

        // Act
        List<MatchResponse> result = matchService.getAllMatches();

        // Assert
        Assertions.assertEquals(2, result.size());

        MatchResponse response1 = result.get(0);
        Assertions.assertEquals("match1-code", response1.getMatchCode());
        Assertions.assertEquals("FC Barcelona", response1.getHomeTeam().getName());
        Assertions.assertEquals("Real Madrid", response1.getAwayTeam().getName());

        MatchResponse response2 = result.get(1);
        Assertions.assertEquals("match2-code", response2.getMatchCode());
        Assertions.assertEquals("Manchester United", response2.getHomeTeam().getName());
        Assertions.assertEquals("Liverpool FC", response2.getAwayTeam().getName());
    }

    @Test
    void testGetMatchByMatchCode() {
        // Arrange
        when(matchRepository.findMatchByMatchCode("match1-code"))
                .thenReturn(Optional.of(match1));

        Mockito.when(webClient.get()).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.uri(Mockito.anyString())).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        Mockito.when(responseSpec.bodyToMono(TeamResponse.class))
                .thenReturn(Mono.just(team1), Mono.just(team2));

        // Act
        MatchResponse response = matchService.getMatchByMatchCode("match1-code");

        // Assert
        assertNotNull(response);
        assertEquals(match1.getMatchCode(), response.getMatchCode());
    }

    @Test
    void testGetMatchByMatchCode_ReturnNull() {
        // Arrange
        when(matchRepository.findMatchByMatchCode("non-existing-match-code"))
                .thenReturn(Optional.empty());

        // Act
        MatchResponse response = matchService.getMatchByMatchCode("non-existing-match-code");

        // Assert
        assertNull(response);
    }

    @Test
    void testCreateMatch() {
        // Arrange
        Match match = Match.builder()
                .matchCode(UUID.randomUUID().toString())
                .homeTeamCode(matchRequest.getHomeTeamCode())
                .awayTeamCode(matchRequest.getAwayTeamCode())
                .dateTime(matchRequest.getDateTime())
                .location(matchRequest.getLocation())
                .homeTeamScore(matchRequest.getHomeTeamScore())
                .awayTeamScore(matchRequest.getAwayTeamScore())
                .build();

        when(matchRepository.save(any(Match.class))).thenReturn(match);

        Mockito.when(webClient.get()).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.uri(Mockito.anyString())).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        Mockito.when(responseSpec.bodyToMono(TeamResponse.class))
                .thenReturn(Mono.just(team1), Mono.just(team2));

        // Act
        MatchResponse result = matchService.createMatch(matchRequest);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(match.getMatchCode(), result.getMatchCode());
        Assertions.assertEquals(match.getHomeTeamCode(), result.getHomeTeam().getTeamCode());
        Assertions.assertEquals(match.getAwayTeamCode(), result.getAwayTeam().getTeamCode());
        Assertions.assertEquals(match.getDateTime(), result.getDateTime());
        Assertions.assertEquals(match.getLocation(), result.getLocation());
        Assertions.assertEquals(match.getHomeTeamScore(), result.getHomeTeamScore());
        Assertions.assertEquals(match.getAwayTeamScore(), result.getAwayTeamScore());
    }

    @Test
    void testEditTeam() {
        // Arrange
        when(matchRepository.findMatchByMatchCode(match1.getMatchCode()))
                .thenReturn(Optional.of(match1));

        matchRequest.setDateTime(LocalDateTime.of(2025, 1, 1, 20, 30));

        Match updatedMatch = Match.builder()
                .dateTime(matchRequest.getDateTime())
                .matchCode("match1-code")
                .build();

        when(matchRepository.save(any(Match.class))).thenReturn(updatedMatch);

        Mockito.when(webClient.get()).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.uri(Mockito.anyString())).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        Mockito.when(responseSpec.bodyToMono(TeamResponse.class))
                .thenReturn(Mono.just(team1), Mono.just(team2));

        // Act
        MatchResponse response = matchService.editMatch(match1.getMatchCode(), matchRequest);

        // Assert
        assertNotNull(response);
        assertEquals(matchRequest.getDateTime(), response.getDateTime());
        assertEquals(match1.getMatchCode(), response.getMatchCode());
    }

    @Test
    void TestEditMatch_MatchNotFound() {
        // Arrange
        when(matchRepository.findMatchByMatchCode(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> matchService.editMatch("non-existing-match-code", matchRequest));

        assertEquals("Match not found with code: non-existing-match-code", exception.getMessage());
    }

    @Test
    void testRemoveTeam() {
        // Arrange
        when(matchRepository.findMatchByMatchCode(match1.getMatchCode()))
                .thenReturn(Optional.of(match1));

        Mockito.when(webClient.get()).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.uri(Mockito.anyString())).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        Mockito.when(responseSpec.bodyToMono(TeamResponse.class))
                .thenReturn(Mono.just(team1), Mono.just(team2));

        // Act
        MatchResponse response = matchService.removeMatch(match1.getMatchCode());

        // Assert
        assertNotNull(response);
        assertEquals(match1.getMatchCode(), response.getMatchCode());

        verify(matchRepository, times(1)).delete(match1);
    }

    @Test
    void TestRemoveMatch_MatchNotFound() {
        // Arrange
        when(matchRepository.findMatchByMatchCode(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> matchService.removeMatch("non-existing-match-code"));

        assertEquals("Match not found with code: non-existing-match-code", exception.getMessage());
        verify(matchRepository, times(0)).delete(any(Match.class));
    }
}