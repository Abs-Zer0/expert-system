package chess.ai.repositories.kb;

import chess.ai.models.kb.Target;
import org.springframework.data.repository.CrudRepository;

public interface TargetRepository extends CrudRepository<Target, String> {
}
