package fact.it.playerservice.repository;


import fact.it.playerservice.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends MongoRepository<Player, String> {
    Optional<List<Player>> findPlayersByTeamCode(String teamCode);
}
