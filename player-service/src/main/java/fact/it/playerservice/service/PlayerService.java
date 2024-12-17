package fact.it.playerservice.service;

import fact.it.playerservice.dto.PlayerRequest;
import fact.it.playerservice.dto.PlayerResponse;
import fact.it.playerservice.model.Player;
import fact.it.playerservice.repository.PlayerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    @PostConstruct
    public void loadData() {
        if (playerRepository.count() <= 0) {
            List<Player> players = List.of(
                    Player.builder()
                            .firstName("Lionel")
                            .lastName("Messi")
                            .position("Forward")
                            .birthDate(LocalDate.of(1987, 6, 24))
                            .nationality("Argentinian")
                            .playerCode("c1f8e5d4-9a3d-4b8e-8f3a-2c6d7b1e9f4c")
                            .teamCode("b9e4d4b8-3c5a-4b5d-bae3-1f3c74d94c4b") // FC Barcelona
                            .build(),
                    Player.builder()
                            .firstName("Cristiano")
                            .lastName("Ronaldo")
                            .position("Forward")
                            .birthDate(LocalDate.of(1985, 2, 5))
                            .nationality("Portuguese")
                            .playerCode("a5e7c8d4-3b9a-4f6e-9c3d-6d1a2f8b7e9c")
                            .teamCode("a5a0e8e4-1a2b-4f6c-8e3e-cd1a2f9b28e6") // Real Madrid
                            .build(),
                    Player.builder()
                            .firstName("Virgil")
                            .lastName("van Dijk")
                            .position("Defender")
                            .birthDate(LocalDate.of(1991, 7, 8))
                            .nationality("Dutch")
                            .playerCode("f3a6b8e9-2c7d-4b9f-8d3a-9e5c1f7a2b4d")
                            .teamCode("a7b9c4d6-2e1a-489c-8f9e-3b4d6f7a8c9e") // Liverpool FC
                            .build(),
                    Player.builder()
                            .firstName("Marcus")
                            .lastName("Rashford")
                            .position("Attacker")
                            .birthDate(LocalDate.of(1997, 10, 31))
                            .nationality("English")
                            .playerCode("d1c8e7f9-3b4d-4c2a-8e1f-7a5d9f2b6e8c")
                            .teamCode("c7d2f312-2b8d-4014-9028-637fe3a49e56") // Manchester United
                            .build(),
                    Player.builder()
                            .firstName("Kylian")
                            .lastName("Mbappé")
                            .position("Forward")
                            .birthDate(LocalDate.of(1998, 12, 20))
                            .nationality("French")
                            .playerCode("e4f8d1a6-9c3a-4b8e-8f7c-1d2b6a9e5f3c")
                            .teamCode("e4f8bc7b-b9d8-4c19-a91d-0b31a2c8e9b2") // PSG
                            .build(),
                    Player.builder()
                            .firstName("Manuel")
                            .lastName("Neuer")
                            .position("Goalkeeper")
                            .birthDate(LocalDate.of(1986, 3, 27))
                            .nationality("German")
                            .playerCode("d8e7b1f9-6c3a-4f2e-8d7c-9a1b5e3f6a8d")
                            .teamCode("d0934c69-85a3-4638-947c-df4f3c8b7dc9") // Bayern
                            .build(),
                    Player.builder()
                            .firstName("Sergio")
                            .lastName("Ramos")
                            .position("Defender")
                            .birthDate(LocalDate.of(1986, 3, 30))
                            .nationality("Spanish")
                            .playerCode("f1a6e9b8-4c3d-2f7e-8d1b-9a5d3c7e6f8b")
                            .teamCode("a5a0e8e4-1a2b-4f6c-8e3e-cd1a2f9b28e6") // Real Madrid
                            .build(),
                    Player.builder()
                            .firstName("Neymar")
                            .lastName("Jr")
                            .position("Forward")
                            .birthDate(LocalDate.of(1992, 2, 5))
                            .nationality("Brazilian")
                            .playerCode("e9f7c1a6-3d8b-4b2e-8f3c-6a9d5e7b1f4d")
                            .teamCode("e4f8bc7b-b9d8-4c19-a91d-0b31a2c8e9b2") // PSG
                            .build(),
                    Player.builder()
                            .firstName("Robert")
                            .lastName("Lewandowski")
                            .position("Forward")
                            .birthDate(LocalDate.of(1988, 8, 21))
                            .nationality("Polish")
                            .playerCode("d9e8f1b7-2c4a-4f6e-8b3d-7a9c5e1f8a2d")
                            .teamCode("d0934c69-85a3-4638-947c-df4f3c8b7dc9") // Bayern
                            .build()
            );

            playerRepository.saveAll(players);
            System.out.println("10 players have been saved to the database with UUID player codes.");
        }
    }

    public List<PlayerResponse> getAllPlayers(){
        List<Player> players = playerRepository.findAll();
        return players.stream().map(this::mapToPlayerResponse).toList();
    }
    public List<PlayerResponse> getPlayersByTeamCode(String teamCode){
        return playerRepository.findPlayersByTeamCode(teamCode).get().stream().map(this::mapToPlayerResponse).toList();

    }
    public PlayerResponse getPlayerByPlayerCode(String teamCode) {
        Optional<Player> team = playerRepository.findPlayerByPlayerCode(teamCode);
        return team.map(this::mapToPlayerResponse).orElse(null);
    }

    public PlayerResponse createPlayer(PlayerRequest playerRequest) {
        Player player = Player.builder()
                .firstName(playerRequest.getFirstName())
                .lastName(playerRequest.getLastName())
                .position(playerRequest.getPosition())
                .teamCode(playerRequest.getTeamCode())
                .birthDate(playerRequest.getBirthDate())
                .nationality(playerRequest.getNationality())
                .playerCode(UUID.randomUUID().toString())
                .build();

        Player savedPlayer = playerRepository.save(player);
        return mapToPlayerResponse(savedPlayer);
    }

    public PlayerResponse editPlayer(String playerCode, PlayerRequest playerRequest) {
        Player player = playerRepository.findPlayerByPlayerCode(playerCode)
                .orElseThrow(() -> new RuntimeException("Player not found with code: " + playerCode));

        updateIfNotNull(playerRequest.getFirstName(), player::setFirstName);
        updateIfNotNull(playerRequest.getLastName(), player::setLastName);
        updateIfNotNull(playerRequest.getPosition(), player::setPosition);
        updateIfNotNull(playerRequest.getTeamCode(), player::setTeamCode);
        updateIfNotNull(playerRequest.getBirthDate(), player::setBirthDate);
        updateIfNotNull(playerRequest.getNationality(), player::setNationality);

        Player updatedPlayer = playerRepository.save(player);
        return mapToPlayerResponse(updatedPlayer);
    }

    private <T> void updateIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }


    public boolean removePlayer(String playerCode) {
        Optional<Player> optionalPlayer = playerRepository.findPlayerByPlayerCode(playerCode);
        if (optionalPlayer.isPresent()) {
            playerRepository.delete(optionalPlayer.get());
            return true;
        } else {
            return false;
        }
    }

    private PlayerResponse mapToPlayerResponse(Player player){
        return PlayerResponse.builder()
                .playerCode(player.getPlayerCode())
                .firstName(player.getFirstName())
                .lastName(player.getLastName())
                .position(player.getPosition())
                .teamCode(player.getTeamCode())
                .birthDate(player.getBirthDate())
                .nationality(player.getNationality())
                .build();
    }

}
