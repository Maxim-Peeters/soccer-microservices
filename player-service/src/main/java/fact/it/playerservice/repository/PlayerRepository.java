package fact.it.playerservice.repository;


import fact.it.playerservice.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRepository extends MongoRepository<Player, String> {
}
