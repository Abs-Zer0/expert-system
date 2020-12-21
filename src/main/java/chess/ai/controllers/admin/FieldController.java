package chess.ai.controllers.admin;

import chess.ai.controllers.BaseController;
import chess.ai.services.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(path = "/admin/field/**")
public class FieldController extends BaseController {
    @Autowired
    private FieldService field;

    @GetMapping("/")
    public ModelAndView index(Model model) {
        return OK(model, "admin/field");
    }
}
