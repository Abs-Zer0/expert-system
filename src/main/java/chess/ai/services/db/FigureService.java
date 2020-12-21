package chess.ai.services.db;

import chess.ai.models.db.Figure;
import chess.ai.repositories.db.FigureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FigureService {
    @Autowired
    private FigureRepository repo;

    public List<Figure> getAll() {
        return (List<Figure>) repo.findAll();
    }

    public Figure get(String name) throws Exception {
        final Optional<Figure> figure = repo.findById(name);
        if (figure.isEmpty())
            throw new Exception("Figure with name='" + name + "' not found in database");

        return figure.get();
    }

    public void addOrUpdate(Figure figure) {
        repo.save(figure);
    }

    public Figure remove(String name) throws Exception {
        final Figure result = get(name);
        repo.delete(result);

        return result;
    }

    public boolean isSameExist(Figure figure) {
        return repo.findById(figure.getName()).isPresent();
    }
}
