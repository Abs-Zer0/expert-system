package chess.ai.services;

import chess.ai.models.db.Figure;
import chess.ai.services.db.FigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FieldService {

    @Autowired
    private FigureService figures;

    public Figure[][] buildFieldFromJson(String[][] jsonField) {
        final int height = jsonField.length;
        if (height == 0) {
            return new Figure[0][];
        }
        final int width = jsonField[0].length;

        final Figure[][] field = new Figure[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                field[i][j] = figureFromJson(jsonField[i][j]);
            }
        }

        return field;
    }

    private Figure figureFromJson(String json) {
        if (json.trim().isEmpty()) {
            return null;
        }

        final boolean isEnemy = json.matches("(\\w+) \\[E\\]$");
        final String name = isEnemy ? json.replace(" [E]", "") : json;

        try {
            final Figure figure = figures.get(name);
            figure.setIsEnemy(isEnemy);

            return figure;
        } catch (Exception ex) {
            return null;
        }
    }
}
