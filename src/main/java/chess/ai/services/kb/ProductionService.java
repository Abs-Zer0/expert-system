package chess.ai.services.kb;

import chess.ai.models.kb.Production;
import chess.ai.repositories.kb.ProductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductionService {
    @Autowired
    private ProductionRepository repo;

    public List<Production> getAll() {
        return (List<Production>) repo.findAll();
    }

    public Production get(long id) throws Exception {
        final Optional<Production> production = repo.findById(id);
        if (production.isEmpty())
            throw new Exception("Production with id='" + id + "' not found in database");

        return production.get();
    }

    public void addOrUpdate(Production production) {
        repo.save(production);
    }

    public Production remove(long id) throws Exception {
        final Production result = get(id);
        repo.delete(result);

        return result;
    }
}
