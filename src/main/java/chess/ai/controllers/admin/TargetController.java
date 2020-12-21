package chess.ai.controllers.admin;

import chess.ai.controllers.BaseController;
import chess.ai.services.kb.TargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/admin/targets/**")
public class TargetController extends BaseController {
    @Autowired
    private TargetService targets;
}
