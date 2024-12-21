package fact.it.teamservice;

import fact.it.teamservice.dto.PlayerResponse;
import fact.it.teamservice.dto.TeamRequest;
import fact.it.teamservice.dto.TeamResponse;
import fact.it.teamservice.model.Team;
import fact.it.teamservice.repository.TeamRepository;
import fact.it.teamservice.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class TeamServiceUnitTests {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private Team team1;
    private Team team2;
    private TeamRequest teamRequest;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(teamService, "playerServiceBaseurl", "localhost:8080");

        // Initialize teamRequest and teams for testing
        teamRequest = new TeamRequest();
        teamRequest.setName("Manchester City FC");
        teamRequest.setCity("Manchester");
        teamRequest.setCountry("England");

        team1 = Team.builder()
                .name("Manchester City FC")
                .city("Manchester")
                .country("England")
                .teamCode("team-1-code")
                .build();

        team2 = Team.builder()
                .name("RSC Anderlecht")
                .city("Brussels")
                .country("Belgium")
                .teamCode("team-2-code")
                .build();
    }

    @Test
    void testGetAllTeams() {
        // Arrange
        when(teamRepository.findAll()).thenReturn(List.of(team1, team2));

        // Mock WebClient response for player data
        PlayerResponse playerResponse = new PlayerResponse();

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        // Use ParameterizedTypeReference for stubbing
        when(responseSpec.bodyToMono(ArgumentMatchers.any(ParameterizedTypeReference.class)))
                .thenReturn(Mono.just(List.of(playerResponse))); // Return a List instead of an array

        // Act
        List<TeamResponse> responses = teamService.getAllTeams();

        // Assert
        assertEquals(2, responses.size());
        assertEquals(team1.getTeamCode(), responses.get(0).getTeamCode());
        assertEquals(team2.getTeamCode(), responses.get(1).getTeamCode());
    }

    @Test
    void testGetTeamByTeamCode() {
        // Arrange
        when(teamRepository.findTeamByTeamCode("team-1-code"))
                .thenReturn(Optional.of(team1));

        // Mock WebClient response for player data
        PlayerResponse playerResponse = new PlayerResponse();

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        // Use ParameterizedTypeReference for stubbing
        when(responseSpec.bodyToMono(ArgumentMatchers.any(ParameterizedTypeReference.class)))
                .thenReturn(Mono.just(List.of(playerResponse))); // Return a List instead of an array

        // Act
        TeamResponse response = teamService.getTeamByTeamCode("team-1-code");

        // Assert
        assertNotNull(response);
        assertEquals(team1.getTeamCode(), response.getTeamCode());
    }

    @Test
    void testGetTeamByTeamCode_ReturnNull() {
        // Arrange
        when(teamRepository.findTeamByTeamCode("non-existing-team-code"))
                .thenReturn(Optional.empty());

        // Act
        TeamResponse response = teamService.getTeamByTeamCode("non-existing-team-code");

        // Assert
        assertNull(response);
    }

    @Test
    void testCreateTeam() {
        // Arrange
        Team team = Team.builder()
                .name(teamRequest.getName())
                .city(teamRequest.getCity())
                .country(teamRequest.getCountry())
                .teamCode(UUID.randomUUID().toString())
                .build();

        when(teamRepository.save(any(Team.class))).thenReturn(team);

        // Mock WebClient response for player data
        PlayerResponse playerResponse = new PlayerResponse();

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        // Use ParameterizedTypeReference for stubbing
        when(responseSpec.bodyToMono(ArgumentMatchers.any(ParameterizedTypeReference.class)))
                .thenReturn(Mono.just(List.of(playerResponse))); // Return a List instead of an array

        // Act
        TeamResponse response = teamService.createTeam(teamRequest);

        // Assert
        assertNotNull(response);
        assertEquals(team.getName(), response.getName());
        assertEquals(team.getCity(), response.getCity());
        assertEquals(team.getCountry(), response.getCountry());
        assertNotNull(response.getTeamCode());
    }

    @Test
    void testEditTeam() {
        // Arrange
        when(teamRepository.findTeamByTeamCode(team1.getTeamCode()))
                .thenReturn(Optional.of(team1));

        teamRequest.setName("FC Liverpool");
        teamRequest.setCity("Liverpool");
        teamRequest.setCountry("England");

        Team updatedTeam = Team.builder()
                .name(teamRequest.getName())
                .country(teamRequest.getCountry())
                .city(teamRequest.getCity())
                .teamCode("team-1-code")
                .build();

        when(teamRepository.save(any(Team.class))).thenReturn(updatedTeam);

        // Mock WebClient response for player data
        PlayerResponse playerResponse = new PlayerResponse();

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        // Use ParameterizedTypeReference for stubbing
        when(responseSpec.bodyToMono(ArgumentMatchers.any(ParameterizedTypeReference.class)))
                .thenReturn(Mono.just(List.of(playerResponse))); // Return a List instead of an array

        // Act
        TeamResponse response = teamService.editTeam(team1.getTeamCode(), teamRequest);

        // Assert
        assertNotNull(response);
        assertEquals(teamRequest.getName(), response.getName());
        assertEquals(teamRequest.getCity(), response.getCity());
        assertEquals(teamRequest.getCountry(), response.getCountry());
        assertEquals(team1.getTeamCode(), response.getTeamCode());
    }

    @Test
    void TestEditTeam_TeamNotFound() {
        // Arrange
        when(teamRepository.findTeamByTeamCode(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> teamService.editTeam("non-existing-team-code", teamRequest));


        assertEquals("Team not found with code: non-existing-team-code", exception.getMessage());
    }

    @Test
    void testRemoveTeam() {
        // Arrange
        when(teamRepository.findTeamByTeamCode(team1.getTeamCode()))
                .thenReturn(Optional.of(team1));

        // Mock WebClient response for player data
        PlayerResponse playerResponse = new PlayerResponse();

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        // Use ParameterizedTypeReference for stubbing
        when(responseSpec.bodyToMono(ArgumentMatchers.any(ParameterizedTypeReference.class)))
                .thenReturn(Mono.just(List.of(playerResponse))); // Return a List instead of an array

        // Act
        TeamResponse response = teamService.removeTeam(team1.getTeamCode());

        // Assert
        assertNotNull(response);
        assertEquals(team1.getName(), response.getName());
        assertEquals(team1.getCity(), response.getCity());
        assertEquals(team1.getCountry(), response.getCountry());
        assertEquals(team1.getTeamCode(), response.getTeamCode());

        verify(teamRepository, times(1)).delete(team1);
    }

    @Test
    void testRemoveTeam_TeamNotFound() {
        // Arrange
        when(teamRepository.findTeamByTeamCode(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> teamService.removeTeam("non-existing-team-code"));


        assertEquals("Team not found with code: non-existing-team-code", exception.getMessage());
        verify(teamRepository, times(0)).delete(any(Team.class));
    }
}
