package fact.it.teamservice.repository;

import fact.it.teamservice.model.Team;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface TeamRepository extends JpaRepository<Team, Long> {
}
