package chess.ai.repositories.kb;

import chess.ai.models.kb.Fact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FactRepository extends CrudRepository<Fact, String> {
    Iterable<Fact> findAllByIsTarget(boolean isTarget);

    @Query(value = "SELECT * FROM FACTS WHERE LOWER(name) LIKE '%?1%'", nativeQuery = true)
    Iterable<Fact> findByNameLike(String tmp);
}
