package chess.ai.repositories.kb;

import chess.ai.models.kb.Fact;
import chess.ai.models.kb.Target;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TargetRepository extends CrudRepository<Target, Long> {
    Optional<Target> findByFact(Fact fact);
}
