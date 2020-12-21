package chess.ai.repositories.db;

import chess.ai.models.db.Line;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LineRepository extends CrudRepository<Line, Long> {
    Optional<Line> findByDxAndDyAndMaxLengthAndLabel(int dx, int dy, int maxLength, String label);
}
