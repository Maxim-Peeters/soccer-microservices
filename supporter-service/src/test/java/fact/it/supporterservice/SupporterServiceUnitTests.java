package fact.it.supporterservice;

import fact.it.supporterservice.dto.PlayerResponse;
import fact.it.supporterservice.dto.SupporterRequest;
import fact.it.supporterservice.dto.SupporterResponse;
import fact.it.supporterservice.dto.TeamResponse;
import fact.it.supporterservice.model.Supporter;
import fact.it.supporterservice.repository.SupporterRepository;
import fact.it.supporterservice.service.SupporterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupporterServiceUnitTests {

    @Mock
    private SupporterRepository supporterRepository;

    @InjectMocks
    private SupporterService supporterService;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private Supporter supporter1;
    private Supporter supporter2;
    private SupporterRequest supporterRequest;
    private TeamResponse team1;
    private TeamResponse team2;
    private PlayerResponse player1;
    private PlayerResponse player2;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(supporterService, "playerServiceBaseUrl", "localhost:8080");
        ReflectionTestUtils.setField(supporterService, "teamServiceBaseUrl", "localhost:8081");

        supporter1 = Supporter.builder()
                .supporterCode("supporter1-code")
                .firstName("Test")
                .lastName("Supporter 1")
                .email("test.supporter1@example.com")
                .birthDate(LocalDate.of(2000, 1, 1))
                .playerCode("player1-code")
                .teamCode("team1-code")
                .build();

        supporter2 = Supporter.builder()
                .supporterCode("supporter2-code")
                .firstName("Test")
                .lastName("Supporter 2")
                .email("test.supporter2@example.com")
                .birthDate(LocalDate.of(2001, 1, 1))
                .playerCode("player2-code")
                .teamCode("team2-code")
                .build();

        supporterRequest = SupporterRequest.builder()
                .firstName("Supporter")
                .lastName("Request")
                .email("supporter.request@example.com")
                .birthDate(LocalDate.of(2002, 1, 1))
                .playerCode("player1-code")
                .teamCode("team2-code")
                .build();

        team1 = TeamResponse.builder()
                .teamCode("team1-code")
                .name("FC Barcelona")
                .build();

        team2 = TeamResponse.builder()
                .teamCode("team2-code")
                .name("Real Madrid")
                .build();

        player1 = PlayerResponse.builder()
                .playerCode("player1-code")
                .firstName("Cristiano")
                .lastName("Ronaldo")
                .build();

        player2 = PlayerResponse.builder()
                .playerCode("player2-code")
                .firstName("Lionel")
                .lastName("Messi")
                .build();
    }

    @Test
    void testGetAllSupporters() {
        // Arrange
        when(supporterRepository.findAll()).thenReturn(List.of(supporter1, supporter2));

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(Mockito.anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.bodyToMono(TeamResponse.class))
                .thenReturn(Mono.just(team1), Mono.just(team2));
        when(responseSpec.bodyToMono(PlayerResponse.class))
                .thenReturn(Mono.just(player1), Mono.just(player2));

        // Act
        List<SupporterResponse> result = supporterService.getAllSupporters();

        // Assert
        Assertions.assertEquals(2, result.size());

        SupporterResponse response1 = result.get(0);
        Assertions.assertEquals(supporter1.getSupporterCode(), response1.getSupporterCode());
        Assertions.assertEquals(supporter1.getPlayerCode(), response1.getFavoritePlayer().getPlayerCode());
        Assertions.assertEquals(supporter1.getTeamCode(), response1.getTeamName().getTeamCode());

        SupporterResponse response2 = result.get(1);
        Assertions.assertEquals(supporter2.getSupporterCode(), response2.getSupporterCode());
        Assertions.assertEquals(supporter2.getPlayerCode(), response2.getFavoritePlayer().getPlayerCode());
        Assertions.assertEquals(supporter2.getTeamCode(), response2.getTeamName().getTeamCode());
    }

    @Test
    void testGetSupporterBySupporterCode() {
        // Arrange
        when(supporterRepository.findSupporterBySupporterCode("supporter1-code")).thenReturn(Optional.of(supporter1));

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(Mockito.anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.bodyToMono(TeamResponse.class))
                .thenReturn(Mono.just(team1), Mono.just(team2));
        when(responseSpec.bodyToMono(PlayerResponse.class))
                .thenReturn(Mono.just(player1), Mono.just(player2));

        // Act
        SupporterResponse response = supporterService.getSupporterBySupporterCode("supporter1-code");

        // Assert
        assertNotNull(response);
        assertEquals(supporter1.getSupporterCode(), response.getSupporterCode());
    }

    @Test
    void testGetSupporterBySupporterCode_ReturnNull() {
        // Arrange
        when(supporterRepository.findSupporterBySupporterCode("non-existing-supporter-code"))
                .thenReturn(Optional.empty());

        // Act
        SupporterResponse response = supporterService.getSupporterBySupporterCode("non-existing-supporter-code");

        // Assert
        assertNull(response);
    }

    @Test
    void testCreateSupporter() {
        // Arrange
        Supporter supporter = Supporter.builder()
                .supporterCode(UUID.randomUUID().toString())
                .firstName("Create")
                .lastName("Player")
                .email("player.create@example.com")
                .birthDate(LocalDate.of(2000, 2, 2))
                .teamCode("team1-code")
                .playerCode("player2-code")
                .build();

        when(supporterRepository.save(any(Supporter.class))).thenReturn(supporter);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(Mockito.anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.bodyToMono(TeamResponse.class))
                .thenReturn(Mono.just(team1));
        when(responseSpec.bodyToMono(PlayerResponse.class))
                .thenReturn(Mono.just(player2));

        // Act
        SupporterResponse result = supporterService.createSupporter(supporterRequest);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(supporter.getSupporterCode(), result.getSupporterCode());
        Assertions.assertEquals(supporter.getTeamCode(), result.getTeamName().getTeamCode());
        Assertions.assertEquals(supporter.getPlayerCode(), result.getFavoritePlayer().getPlayerCode());
        Assertions.assertEquals(supporter.getFirstName(), result.getFirstName());
        Assertions.assertEquals(supporter.getLastName(), result.getLastName());
        Assertions.assertEquals(supporter.getBirthDate(), result.getBirthDate());
        Assertions.assertEquals(supporter.getEmail(), result.getEmail());
    }

    @Test
    void testEditSupporter() {
        // Arrange
        when(supporterRepository.findSupporterBySupporterCode(supporter1.getSupporterCode()))
                .thenReturn(Optional.of(supporter1));

        supporterRequest.setPlayerCode(player2.getPlayerCode());

        Supporter updatedSupporter = Supporter.builder()
                .playerCode(supporterRequest.getPlayerCode())
                .supporterCode(supporter1.getSupporterCode())
                .build();

        when(supporterRepository.save(any(Supporter.class))).thenReturn(updatedSupporter);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(Mockito.anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.bodyToMono(TeamResponse.class))
                .thenReturn(Mono.just(team1));
        when(responseSpec.bodyToMono(PlayerResponse.class))
                .thenReturn(Mono.just(player2));

        // Act
        SupporterResponse response = supporterService.editSupporter(supporter1.getSupporterCode(), supporterRequest);

        // Assert
        assertNotNull(response);
        assertEquals(supporterRequest.getPlayerCode(), response.getFavoritePlayer().getPlayerCode());
        assertEquals(supporter1.getSupporterCode(), response.getSupporterCode());
    }

    @Test
    void testEditSupporter_SupporterNotFound() {
        // Arrange
        when(supporterRepository.findSupporterBySupporterCode(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, ()
                -> supporterService.editSupporter("non-existing-supporter-code", supporterRequest));

        assertEquals("Supporter not found with code: non-existing-supporter-code", exception.getMessage());
    }

    @Test
    void testRemoveSupporter() {
        // Arrange
        when(supporterRepository.findSupporterBySupporterCode(supporter1.getSupporterCode()))
                .thenReturn(Optional.of(supporter1));

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(Mockito.anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.bodyToMono(TeamResponse.class))
                .thenReturn(Mono.just(team1));
        when(responseSpec.bodyToMono(PlayerResponse.class))
                .thenReturn(Mono.just(player1));

        // Act
        SupporterResponse response = supporterService.removeSupporter(supporter1.getSupporterCode());

        // Assert
        assertNotNull(response);
        assertEquals(supporter1.getSupporterCode(), response.getSupporterCode());
        Assertions.assertEquals(supporter1.getPlayerCode(), response.getFavoritePlayer().getPlayerCode());
        Assertions.assertEquals(supporter1.getTeamCode(), response.getTeamName().getTeamCode());

        verify(supporterRepository, times(1)).delete(supporter1);
    }

    @Test
    void testRemoveSupporter_SupporterNotFound() {
        // Arrange
        when(supporterRepository.findSupporterBySupporterCode(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, ()
                -> supporterService.removeSupporter("non-existing-supporter-code"));

        assertEquals("Supporter not found with code: non-existing-supporter-code", exception.getMessage());
        verify(supporterRepository, times(0)).delete(any(Supporter.class));
    }
}
