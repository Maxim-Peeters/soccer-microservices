package fact.it.supporterservice.repository;

import fact.it.supporterservice.model.Supporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupporterRepository extends JpaRepository<Supporter, Long> {
}
