package fact.it.playerservice;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import fact.it.playerservice.dto.PlayerRequest;
import fact.it.playerservice.dto.PlayerResponse;
import fact.it.playerservice.model.Player;
import fact.it.playerservice.repository.PlayerRepository;
import fact.it.playerservice.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class PlayerServiceApplicationTests {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    private Player player1;
    private Player player2;
    private PlayerRequest playerRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize playerRequest and players for testing
        playerRequest = new PlayerRequest();
        playerRequest.setFirstName("John");
        playerRequest.setLastName("Doe");
        playerRequest.setPosition("Forward");
        playerRequest.setBirthDate(LocalDate.of(1990, 1, 1));
        playerRequest.setNationality("American");
        playerRequest.setTeamCode("TeamA");

        player1 = Player.builder()
                .firstName("John")
                .lastName("Doe")
                .position("Forward")
                .teamCode("TeamA")
                .birthDate(LocalDate.of(1990, 1, 1))
                .nationality("American")
                .playerCode("player-1-code")
                .build();

        player2 = Player.builder()
                .firstName("Jane")
                .lastName("Smith")
                .position("Midfielder")
                .teamCode("TeamB")
                .birthDate(LocalDate.of(1992, 2, 2))
                .nationality("Canadian")
                .playerCode("player-2-code")
                .build();
    }

    @Test
    void testGetAllPlayers() {
        // Arrange
        when(playerRepository.findAll()).thenReturn(List.of(player1, player2));

        // Act
        List<PlayerResponse> responses = playerService.getAllPlayers();

        // Assert
        assertEquals(2, responses.size());
        assertEquals(player1.getPlayerCode(), responses.get(0).getPlayerCode());
        assertEquals(player2.getPlayerCode(), responses.get(1).getPlayerCode());
    }

    @Test
    void testGetPlayersByTeamCode() {
        // Arrange
        when(playerRepository.findPlayersByTeamCode("TeamA"))
                .thenReturn(Optional.of(List.of(player1)));

        // Act
        List<PlayerResponse> responses = playerService.getPlayersByTeamCode("TeamA");

        // Assert
        assertEquals(1, responses.size());
        assertEquals(player1.getPlayerCode(), responses.get(0).getPlayerCode());
    }

    @Test
    void testGetPlayersByTeamCode_NoPlayersFoundForTeam() {
        // Arrange
        when(playerRepository.findPlayersByTeamCode("NonExistentTeam"))
                .thenReturn(Optional.empty());

        // Act & Assert:
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            playerService.getPlayersByTeamCode("NonExistentTeam");
        });

        assertEquals("No players found for team code: NonExistentTeam", exception.getMessage());
    }

    @Test
    void testGetPlayerByPlayerCode() {
        // Arrange
        when(playerRepository.findPlayerByPlayerCode("player-1-code"))
                .thenReturn(Optional.of(player1));

        // Act
        PlayerResponse response = playerService.getPlayerByPlayerCode("player-1-code");

        // Assert
        assertNotNull(response);
        assertEquals(player1.getPlayerCode(), response.getPlayerCode());
    }

    @Test
    void testGetPlayerByPlayerCode_ReturnNull() {
        // Arrange
        when(playerRepository.findPlayerByPlayerCode("non-existing-player-code"))
                .thenReturn(Optional.empty());

        // Act
        PlayerResponse response = playerService.getPlayerByPlayerCode("non-existing-player-code");

        // Assert
        assertNull(response);
    }

    @Test
    void testCreatePlayer() {
        // Arrange
        playerRequest.setTeamCode(null);

        Player player = Player.builder()
                .firstName(playerRequest.getFirstName())
                .lastName(playerRequest.getLastName())
                .position(playerRequest.getPosition())
                .birthDate(playerRequest.getBirthDate())
                .nationality(playerRequest.getNationality())
                .playerCode(UUID.randomUUID().toString())
                .build();

        when(playerRepository.save(any(Player.class))).thenReturn(player);

        // Act
        PlayerResponse response = playerService.createPlayer(playerRequest);

        // Assert
        assertNotNull(response);
        assertEquals(player.getFirstName(), response.getFirstName());
        assertEquals(player.getLastName(), response.getLastName());
        assertEquals(player.getPosition(), response.getPosition());
        assertEquals(player.getBirthDate(), response.getBirthDate());
        assertEquals(player.getNationality(), response.getNationality());
        assertNull(response.getTeamCode());
        assertNotNull(response.getPlayerCode());
    }

    @Test
    void testEditPlayer() {
        // Arrange
        when(playerRepository.findPlayerByPlayerCode(player1.getPlayerCode()))
                .thenReturn(Optional.of(player1));

        playerRequest.setFirstName("John");
        playerRequest.setLastName("Doe");
        playerRequest.setPosition("Forward");
        playerRequest.setTeamCode("TeamA");
        playerRequest.setBirthDate(LocalDate.of(2004,1 ,2));
        playerRequest.setNationality("American");

        Player updatedPlayer = Player.builder()
                .firstName(playerRequest.getFirstName())
                .lastName(playerRequest.getLastName())
                .position(playerRequest.getPosition())
                .teamCode(playerRequest.getTeamCode())
                .birthDate(playerRequest.getBirthDate())
                .nationality(playerRequest.getNationality())
                .playerCode(player1.getPlayerCode())
                .build();

        when(playerRepository.save(any(Player.class))).thenReturn(updatedPlayer);

        // Act
        PlayerResponse response = playerService.editPlayer(player1.getPlayerCode(), playerRequest);

        // Assert
        assertNotNull(response);
        assertEquals(playerRequest.getFirstName(), response.getFirstName());
        assertEquals(playerRequest.getLastName(), response.getLastName());
        assertEquals(playerRequest.getPosition(), response.getPosition());
        assertEquals(playerRequest.getTeamCode(), response.getTeamCode());
        assertEquals(playerRequest.getBirthDate(), response.getBirthDate());
        assertEquals(playerRequest.getNationality(), response.getNationality());
        assertEquals(player1.getPlayerCode(), response.getPlayerCode());
    }

    @Test
    void TestEditPlayer_PlayerNotFound() {
        // Arrange
        when(playerRepository.findPlayerByPlayerCode(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            playerService.editPlayer("non-existing-player-code", playerRequest);
        });

        assertEquals("Player not found with code: non-existing-player-code", exception.getMessage());
    }

    @Test
    void testRemovePlayer() {
        // Arrange
        when(playerRepository.findPlayerByPlayerCode(player1.getPlayerCode()))
                .thenReturn(Optional.of(player1));

        // Act
        PlayerResponse response = playerService.removePlayer(player1.getPlayerCode());

        // Assert
        assertNotNull(response);
        assertEquals(player1.getFirstName(), response.getFirstName());
        assertEquals(player1.getLastName(), response.getLastName());
        assertEquals(player1.getPosition(), response.getPosition());
        assertEquals(player1.getTeamCode(), response.getTeamCode());
        assertEquals(player1.getBirthDate(), response.getBirthDate());
        assertEquals(player1.getNationality(), response.getNationality());
        assertEquals(player1.getPlayerCode(), response.getPlayerCode());

        verify(playerRepository, times(1)).delete(player1);
    }

    @Test
    void testRemovePlayer_PlayerNotFound() {
        // Arrange
        when(playerRepository.findPlayerByPlayerCode(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            playerService.removePlayer("non-existing-player-code");
        });

        assertEquals("Player not found with code: non-existing-player-code", exception.getMessage());
        verify(playerRepository, times(0)).delete(any(Player.class));
    }
}
