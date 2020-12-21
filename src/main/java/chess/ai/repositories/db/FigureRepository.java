package chess.ai.repositories.db;

import chess.ai.models.db.Figure;
import org.springframework.data.repository.CrudRepository;

public interface FigureRepository extends CrudRepository<Figure, String> {
}
