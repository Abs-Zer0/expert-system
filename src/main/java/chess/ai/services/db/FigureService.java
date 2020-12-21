package chess.ai.services.db;

import chess.ai.models.db.Figure;
import chess.ai.repositories.db.FigureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FigureService {
    @Autowired
    private FigureRepository repo;

    public List<Figure> getAll() {
        return (List<Figure>) repo.findAll();
    }

    public void addOrUpdate(Figure figure) {
        final Figure result = figure;
        final Optional<Figure> fig = repo.findById(figure.getName());
        if (fig.isPresent())
            result.setName(fig.get().getName());

        repo.save(result);
    }

    public Figure get(String name) throws Exception {
        final Optional<Figure> figure = repo.findById(name);
        if (figure.isEmpty())
            throw new Exception(new StringBuilder().append("Figure with name='").append(name).append("' not found in database").toString());

        return figure.get();
    }

    public Figure remove(String name) throws Exception {
        final Optional<Figure> figure = repo.findById(name);
        if (figure.isEmpty())
            throw new Exception(new StringBuilder().append("Figure with name='").append(name).append("' not found in database").toString());

        final Figure result = figure.get();
        repo.delete(result);

        return result;
    }

    public boolean isSameExist(Figure figure) {
        return repo.findById(figure.getName()).isPresent();
    }
}
