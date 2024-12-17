package fact.it.matchservice.repository;

import fact.it.matchservice.model.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MatchRepository extends MongoRepository<Match, String> {
    Optional<Match> findMatchByMatchCode(String matchCode);
}
