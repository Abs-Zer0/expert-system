package chess.ai.repositories.kb;

import chess.ai.models.kb.Production;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductionRepository extends CrudRepository<Production, Long> {
    Optional<Production> findByTargetAndFacts(String target, String facts);
}
