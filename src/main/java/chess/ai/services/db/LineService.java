package chess.ai.services.db;

import chess.ai.models.db.Line;
import chess.ai.repositories.db.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LineService {
    @Autowired
    private LineRepository repo;

    public List<Line> getAll() {
        return (List<Line>) repo.findAll();
    }

    public Line get(long id) throws Exception {
        final Optional<Line> line = repo.findById(id);
        if (line.isEmpty())
            throw new Exception(new StringBuilder().append("Figure with id='").append(id).append("' not found in database").toString());

        return line.get();
    }

    public void addOrUpdate(Line line) {
        final Line result = line;
        final Optional<Line> ln = repo.findById(line.getId());
        if (ln.isPresent()) {
            result.setId(ln.get().getId());
        }

        repo.save(result);
    }

    public Line remove(long id) throws Exception {
        final Optional<Line> line = repo.findById(id);
        if (line.isEmpty())
            throw new Exception(new StringBuilder().append("Figure with id='").append(id).append("' not found in database").toString());

        final Line result = line.get();
        repo.delete(result);

        return result;
    }

    public boolean isSameExist(Line line) {
        return repo.findByDxAndDyAndMaxLengthAndLabel(line.getDx(), line.getDy(), line.getMaxLength(), line.getLabel()).isPresent();
    }

    public boolean isSameExistExcludeId(Line line, long id) {
        final Optional<Line> ln = repo.findByDxAndDyAndMaxLengthAndLabel(line.getDx(), line.getDy(), line.getMaxLength(), line.getLabel());
        if (ln.isEmpty())
            return false;

        return id != ln.get().getId();
    }
}
