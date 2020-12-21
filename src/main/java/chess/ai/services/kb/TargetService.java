package chess.ai.services.kb;

import chess.ai.models.kb.Target;
import chess.ai.repositories.kb.TargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TargetService {
    @Autowired
    private TargetRepository repo;

    public List<Target> getAll() {
        return (List<Target>) repo.findAll();
    }

    public Target get(long id) throws Exception {
        final Optional<Target> target = repo.findById(id);
        if (target.isEmpty())
            throw new Exception("Target with id='" + id + "' not found in database");

        return target.get();
    }

    public void addOrUpdateAdmin(Target target) {
        final Target result = target;
        result.setCanRemoved(false);

        addOrUpdate(result);
    }

    public void addOrUpdateExpert(Target target) {
        final Target result = target;
        result.setCanRemoved(true);

        addOrUpdate(result);
    }

    public Target remove(long id) throws Exception {
        final Target result = get(id);
        repo.delete(result);

        return result;
    }

    public boolean isSameExist(Target target) {
        return repo.findByFact(target.getFact()).isPresent();
    }

    private void addOrUpdate(Target target) {
        repo.save(target);
    }
}
