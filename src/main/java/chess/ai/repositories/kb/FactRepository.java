package chess.ai.repositories.kb;

import chess.ai.models.kb.Fact;
import org.springframework.data.repository.CrudRepository;

public interface FactRepository extends CrudRepository<Fact, String> {
}
