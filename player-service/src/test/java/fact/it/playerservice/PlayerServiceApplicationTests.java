package fact.it.playerservice;

import fact.it.playerservice.dto.PlayerRequest;
import fact.it.playerservice.dto.PlayerResponse;
import fact.it.playerservice.model.Player;
import fact.it.playerservice.repository.PlayerRepository;
import fact.it.playerservice.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class PlayerServiceApplicationTests {

	@InjectMocks
	private PlayerService playerService;

	@Mock
	private PlayerRepository playerRepository;

	private Player player;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		player = Player.builder()
				.firstName("Lionel")
				.lastName("Messi")
				.position("Forward")
				.birthDate(LocalDate.of(1987, 6, 24))
				.nationality("Argentinian")
				.playerCode(UUID.randomUUID().toString())
				.teamCode(UUID.randomUUID().toString())
				.build();
	}

	@Test
	void testGetAllPlayers() {
		// Arrange
		Mockito.when(playerRepository.findAll()).thenReturn(List.of(player));

		// Act
		List<PlayerResponse> players = playerService.getAllPlayers();

		// Assert
		assertEquals(1, players.size());
		assertEquals(player.getFirstName(), players.get(0).getFirstName());
		assertEquals(player.getLastName(), players.get(0).getLastName());
	}

	@Test
	void testGetPlayersByTeamCode() {
		// Arrange
		String teamCode = "teamCode";
		Mockito.when(playerRepository.findPlayersByTeamCode(teamCode))
				.thenReturn(Optional.of(List.of(player)));

		// Act
		List<PlayerResponse> players = playerService.getPlayersByTeamCode(teamCode);

		// Assert
		assertNotNull(players);
		assertEquals(1, players.size());
		assertEquals(player.getTeamCode(), players.get(0).getTeamCode());
	}

	@Test
	void testGetPlayersByTeamCodeNoPlayers() {
		// Arrange
		String teamCode = "teamCode";
		Mockito.when(playerRepository.findPlayersByTeamCode(teamCode))
				.thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(NoSuchElementException.class, () -> playerService.getPlayersByTeamCode(teamCode));
	}

	@Test
	void testGetPlayerByPlayerCode() {
		// Arrange
		String playerCode = "playerCode";
		Mockito.when(playerRepository.findPlayerByPlayerCode(playerCode))
				.thenReturn(Optional.of(player));

		// Act
		PlayerResponse response = playerService.getPlayerByPlayerCode(playerCode);

		// Assert
		assertNotNull(response);
		assertEquals(player.getFirstName(), response.getFirstName());
		assertEquals(player.getLastName(), response.getLastName());
	}

	@Test
	void testGetPlayerByPlayerCodeNotFound() {
		// Arrange
		String playerCode = "playerCode";
		Mockito.when(playerRepository.findPlayerByPlayerCode(playerCode))
				.thenReturn(Optional.empty());

		// Act
		PlayerResponse response = playerService.getPlayerByPlayerCode(playerCode);

		// Assert
		assertNull(response);
	}

	@Test
	void testCreatePlayer() {
		// Arrange
		PlayerRequest playerRequest = new PlayerRequest(
				"Kylian",
				"Mbappé",
				"Forward",
				"French",
				LocalDate.of(1998, 12, 20),
				UUID.randomUUID().toString()
		);
		Mockito.when(playerRepository.save(any(Player.class))).thenReturn(player);

		// Act
		PlayerResponse createdPlayer = playerService.createPlayer(playerRequest);

		// Assert
		assertNotNull(createdPlayer);
		assertEquals(player.getFirstName(), createdPlayer.getFirstName());
		assertEquals(player.getLastName(), createdPlayer.getLastName());
	}

	@Test
	void testEditPlayer() {
		// Arrange
		String playerCode = player.getPlayerCode();
		PlayerRequest playerRequest = new PlayerRequest(
				"Kylian",
				"Mbappé",
				"Forward",
				"French",
				LocalDate.of(1998, 12, 20),
				UUID.randomUUID().toString()
		);
		Mockito.when(playerRepository.findPlayerByPlayerCode(playerCode)).thenReturn(Optional.of(player));
		Mockito.when(playerRepository.save(any(Player.class))).thenReturn(player);

		// Act
		PlayerResponse updatedPlayer = playerService.editPlayer(playerCode, playerRequest);

		// Assert
		assertNotNull(updatedPlayer);
		assertEquals(playerRequest.getFirstName(), updatedPlayer.getFirstName());
		assertEquals(playerRequest.getLastName(), updatedPlayer.getLastName());
	}

	@Test
	void testEditPlayerNotFound() {
		// Arrange
		String playerCode = "nonExistentPlayerCode";
		PlayerRequest playerRequest = new PlayerRequest(
				"Kylian",
				"Mbappé",
				"Forward",
				"French",
				LocalDate.of(1998, 12, 20),
				UUID.randomUUID().toString()
		);
		Mockito.when(playerRepository.findPlayerByPlayerCode(playerCode)).thenReturn(Optional.empty());

		// Act & Assert
		RuntimeException exception = assertThrows(RuntimeException.class, () -> playerService.editPlayer(playerCode, playerRequest));
		assertEquals("Player not found with code: " + playerCode, exception.getMessage());
	}

	@Test
	void testRemovePlayer() {
		// Arrange
		String playerCode = player.getPlayerCode();
		Mockito.when(playerRepository.findPlayerByPlayerCode(playerCode)).thenReturn(Optional.of(player));

		// Act
		boolean result = playerService.removePlayer(playerCode);

		// Assert
		assertTrue(result);
		Mockito.verify(playerRepository).delete(player);
	}

	@Test
	void testRemovePlayerNotFound() {
		// Arrange
		String playerCode = "nonExistentPlayerCode";
		Mockito.when(playerRepository.findPlayerByPlayerCode(playerCode)).thenReturn(Optional.empty());

		// Act
		boolean result = playerService.removePlayer(playerCode);

		// Assert
		assertFalse(result);
	}
}
