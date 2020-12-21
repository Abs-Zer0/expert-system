package chess.ai.controllers;

import chess.ai.services.db.FigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController extends BaseController {

    @Autowired
    private FigureService figures;

    @GetMapping("/")
    public ModelAndView index(Model model) {
        return OK(model, "index");
    }

    @GetMapping("/user")
    public ModelAndView user(Model model) {
        model.addAttribute("figures", figures.getAll());

        return OK(model, "user/index");
    }

    @GetMapping("/expert")
    public ModelAndView expert(Model model) {
        return OK(model, "expert/index");
    }

    @GetMapping("/admin")
    public ModelAndView admin(Model model) {
        return OK(model, "admin/index");
    }
}
