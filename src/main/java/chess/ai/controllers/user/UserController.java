package chess.ai.controllers.user;

import chess.ai.models.ExpertResult;
import chess.ai.models.ExpertSolution;
import chess.ai.models.db.Figure;
import chess.ai.services.ExpertSystemService;
import chess.ai.services.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user/**")
public class UserController {
    @Autowired
    private FieldService field;
    @Autowired
    private ExpertSystemService expert;

    @PostMapping("data")
    public ExpertResult data(@RequestBody String[][] jsonField) {
        final Figure[][] figures = field.buildFieldFromJson(jsonField);

        return expert.getSolution(figures);
    }
}
