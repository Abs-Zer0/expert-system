package chess.ai.services;

import chess.ai.models.ExpertResult;
import chess.ai.models.db.Figure;
import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPTerm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class ExpertSystemService {

    public ExpertResult getSolution(Figure[][] field) {
        return new ExpertResult("some result", Arrays.asList(new String[]{"aaa aaaaaaa bbbbbb", "bbb cccccccc yyyyyyyyyyy zzzz"}));
    }

    public void a() {
        JIPEngine engine = new JIPEngine();
        engine.consultFile("tmp.pl");
        JIPTerm goal = engine.getTermParser().parseTerm("?- father(X, Y).");
        JIPQuery q = engine.openSynchronousQuery(goal);
        while (q.hasMoreChoicePoints()) {
            JIPTerm solution = q.nextSolution();
            engine.getTraceListeners();

            System.out.println("X = " + solution.getVariablesTable().get("X"));
            System.out.println("Y = " + solution.getVariablesTable().get("Y"));
        }
    }
}
