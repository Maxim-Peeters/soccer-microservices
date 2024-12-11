package fact.it.matchservice.repository;

import fact.it.matchservice.model.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchRepository extends MongoRepository<Match, String> {
}
