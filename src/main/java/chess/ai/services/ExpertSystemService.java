package chess.ai.services;

import chess.ai.models.ExpertResult;
import chess.ai.models.ExpertSolution;
import chess.ai.models.db.Figure;
import chess.ai.models.db.Line;
import chess.ai.services.kb.FactService;
import chess.ai.services.kb.ProductionService;
import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ExpertSystemService {
    @Autowired
    private FactService facts;
    @Autowired
    private ProductionService productions;

    public ExpertResult getSolution(Figure[][] field) {
        final List<ExpertSolution> solutions = new ArrayList<>();

        try {
            final File f = File.createTempFile("clauses-", ".pl");
            final FileWriter fw = new FileWriter(f);

            fillFigures(fw, field);
            fillProductions(fw);

            final JIPEngine engine = new JIPEngine();
            engine.consultFile(f.getAbsolutePath());

            facts.getTargets().forEach(target -> {
                String term = "?- " + target.getName() + "(";
                for (int i = 0; i < target.getArgsAmount(); i++) {
                    term += (char) ((int) 'A' + i);
                    if (i < (target).getArgsAmount() - 1)
                        term += ',';
                }
                term += ").";

                final JIPQuery query = engine.openSynchronousQuery(term);
                while (query.hasMoreChoicePoints()) {
                    final JIPTerm solution = query.nextSolution();
                    if (solution != null)
                        solutions.add(new ExpertSolution(solution.toString(engine), new ArrayList<>()));
                }
            });

            fw.close();
            f.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ExpertResult(solutions);
    }

    private void fillFigures(FileWriter fw, Figure[][] figures) throws IOException {
        for (int y = 0; y < figures.length; y++) {
            for (int x = 0; x < figures[y].length; x++) {
                final Figure figure = figures[y][x];
                if (figure != null) {
                    final String figureTerm = figure.getName().toLowerCase() + "(" + x + "," + y + ").";
                    fw.write(figureTerm + '\n');

                    if (figure.getIsEnemy()) {
                        final String enemyTerm = "isEnemy(" + x + "," + y + ").";
                        fw.write(enemyTerm + '\n');
                    } else {
                        final String ownTerm = "isOwn(" + x + "," + y + ").";
                        fw.write(ownTerm + '\n');
                    }

                    fillTravelLines(fw, figures, x, y, figure.getIsEnemy());
                    fillAttackLines(fw, figures, x, y, figure.getIsEnemy());
                }
            }
        }
        fw.flush();
    }

    private void fillTravelLines(FileWriter fw, Figure[][] figures, int x, int y, boolean isEnemy) throws IOException {
        final Figure figure = figures[y][x];
        for (int i = 0; i < figure.getTravelLine().size(); i++) {
            Line line = figure.getTravelLine().get(i);
            if (isEnemy)
                line = line.inverse();

            for (int length = 0; length < line.getMaxLength(); length++) {
                int curX = x + line.getDx() * (length + 1);
                int curY = y + line.getDy() * (length + 1);

                if (curX < 0 || curX >= figures[y].length || curY < 0 || curY >= figures.length)
                    break;

                if (figures[curY][curX] == null) {
                    final String term = "canTravel(" + x + "," + y + "," + curX + "," + curY + ").";
                    fw.write(term + '\n');
                } else {
                    break;
                }
            }
        }
    }

    private void fillAttackLines(FileWriter fw, Figure[][] figures, int x, int y, boolean isEnemy) throws IOException {
        final Figure figure = figures[y][x];
        for (int i = 0; i < figure.getFireLine().size(); i++) {
            Line line = figure.getFireLine().get(i);
            if (isEnemy)
                line = line.inverse();

            for (int length = 0; length < line.getMaxLength(); length++) {
                int curX = x + line.getDx() * (length + 1);
                int curY = y + line.getDy() * (length + 1);

                if (curX < 0 || curX >= figures[y].length || curY < 0 || curY >= figures.length)
                    break;

                if (figures[curY][curX] != null) {
                    if (figure.getIsEnemy() != figures[curY][curX].getIsEnemy()) {
                        final String term = "canAttack(" + x + "," + y + "," + curX + "," + curY + ").";
                        fw.write(term + '\n');
                    }

                    break;
                }
            }
        }
    }

    private void fillProductions(FileWriter fw) throws IOException {
        productions.getAll().forEach(production -> {
            final String term = production.getTarget() + ":-" + production.getFacts() + ".";
            try {
                fw.write(term + '\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fw.flush();
    }
}
