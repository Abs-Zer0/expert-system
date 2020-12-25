package chess.ai.services.kb;

import chess.ai.models.kb.Fact;
import chess.ai.repositories.kb.FactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class FactService {
    @Autowired
    private FactRepository repo;

    public List<Fact> getAll() {
        return (List<Fact>) repo.findAll();
    }

    public List<Fact> getTargets() {
        return (List<Fact>) repo.findAllByIsTarget(true);
    }

    public List<Fact> getFactsWithNameLike(String tmp) {
        final List<Fact> all = getAll();
        final Fact[] filtered = all.parallelStream()
                .filter(fact -> fact.getName().toLowerCase().contains(tmp.toLowerCase()))
                .toArray(Fact[]::new);

        return Arrays.asList(filtered);
    }

    public Fact get(String name) throws Exception {
        final Optional<Fact> fact = repo.findById(name);
        if (fact.isEmpty())
            throw new Exception("Fact with name='" + name + "' not found in database");

        return fact.get();
    }

    public void addOrUpdateAdmin(Fact fact) {
        final Fact result = fact;
        result.setCanRemoved(false);

        addOrUpdate(result);
    }

    public void addOrUpdateExpert(Fact fact) {
        final Fact result = fact;
        result.setCanRemoved(true);

        addOrUpdate(result);
    }

    public Fact remove(String name) throws Exception {
        final Fact result = get(name);
        repo.delete(result);

        return result;
    }

    public void remove(Fact fact) {
        repo.delete(fact);
    }

    public boolean isSameExist(Fact fact) {
        return repo.findById(fact.getName()).isPresent();
    }

    private void addOrUpdate(Fact fact) {
        repo.save(fact);
    }
}
