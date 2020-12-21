package chess.ai.repositories.kb;

import chess.ai.models.kb.Production;
import org.springframework.data.repository.CrudRepository;

public interface ProductionRepository extends CrudRepository<Production, Long> {
}
