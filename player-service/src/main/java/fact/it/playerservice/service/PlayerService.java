package fact.it.playerservice.service;

import fact.it.playerservice.dto.PlayerResponse;
import fact.it.playerservice.model.Player;
import fact.it.playerservice.repository.PlayerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
                            .build(),
                    Player.builder()
                            .firstName("Cristiano")
                            .lastName("Ronaldo")
                            .position("Forward")
                            .birthDate(LocalDate.of(1985, 2, 5))
                            .nationality("Portuguese")
                            .build(),
                    Player.builder()
                            .firstName("Virgil")
                            .lastName("van Dijk")
                            .position("Defender")
                            .birthDate(LocalDate.of(1991, 7, 8))
                            .nationality("Dutch")
                            .build(),
                    Player.builder()
                            .firstName("Kevin")
                            .lastName("De Bruyne")
                            .position("Midfielder")
                            .birthDate(LocalDate.of(1991, 6, 28))
                            .nationality("Belgian")
                            .build(),
                    Player.builder()
                            .firstName("Kylian")
                            .lastName("Mbappé")
                            .position("Forward")
                            .birthDate(LocalDate.of(1998, 12, 20))
                            .nationality("French")
                            .build(),
                    Player.builder()
                            .firstName("Manuel")
                            .lastName("Neuer")
                            .position("Goalkeeper")
                            .birthDate(LocalDate.of(1986, 3, 27))
                            .nationality("German")
                            .build(),
                    Player.builder()
                            .firstName("Sergio")
                            .lastName("Ramos")
                            .position("Defender")
                            .birthDate(LocalDate.of(1986, 3, 30))
                            .nationality("Spanish")
                            .build(),
                    Player.builder()
                            .firstName("Luka")
                            .lastName("Modric")
                            .position("Midfielder")
                            .birthDate(LocalDate.of(1985, 9, 9))
                            .nationality("Croatian")
                            .build(),
                    Player.builder()
                            .firstName("Neymar")
                            .lastName("Jr")
                            .position("Forward")
                            .birthDate(LocalDate.of(1992, 2, 5))
                            .nationality("Brazilian")
                            .build(),
                    Player.builder()
                            .firstName("Robert")
                            .lastName("Lewandowski")
                            .position("Forward")
                            .birthDate(LocalDate.of(1988, 8, 21))
                            .nationality("Polish")
                            .build()
            );

            playerRepository.saveAll(players);
            System.out.println("10 players have been saved to the database.");
        }
    }

    public List<PlayerResponse> getAllPlayers(){
        List<Player> players = playerRepository.findAll();
        return players.stream().map(this::mapToPlayerResponse).toList();
    }
    private PlayerResponse mapToPlayerResponse(Player player){
        return PlayerResponse.builder()
                .id(player.getId())
                .firstName(player.getFirstName())
                .lastName(player.getLastName())
                .position(player.getPosition())
                .birthDate(player.getBirthDate())
                .nationality(player.getNationality())
                .build();
    }

}
