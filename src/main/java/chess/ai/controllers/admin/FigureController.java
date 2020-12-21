package chess.ai.controllers.admin;

import chess.ai.controllers.BaseController;
import chess.ai.models.db.Figure;
import chess.ai.models.db.Line;
import chess.ai.services.db.FigureService;
import chess.ai.services.db.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/figures/**")
public class FigureController extends BaseController {

    @Autowired
    private FigureService figures;

    @Autowired
    private LineService lines;

    @GetMapping("/")
    public ModelAndView index(Model model) {
        model.addAttribute("figures", figures.getAll());

        return OK(model, "admin/figures");
    }

    @GetMapping("add")
    public ModelAndView addView(Model model) {
        model.addAttribute("figure", new Figure());
        model.addAttribute("command", "add");
        model.addAttribute("commandMsg", "Добавить");
        model.addAttribute("lines", lines.getAll());

        return OK(model, "admin/figures_edit");
    }

    @PostMapping("add")
    public ModelAndView add(Model model, @ModelAttribute("figure") @Valid Figure figure, BindingResult result, RedirectAttributes attrs) {
        if (result.hasErrors())
            return OK(model, "admin/figures_edit");

        if (figures.isSameExist(figure)) {
            attrs.addFlashAttribute("isError", true);
            attrs.addFlashAttribute("errorMsg", "Такая фигура уже есть");

            return Redirect("add");
        }

        figures.addOrUpdate(figure);

        attrs.addFlashAttribute("isSuccess", true);
        attrs.addFlashAttribute("successMsg", "Фигура успешно добавлена");

        return Redirect("/admin/figures");
    }

    @GetMapping("/admin/figures/edit/{name}")
    public ModelAndView editView(Model model, @PathVariable String name, RedirectAttributes attrs) {
        try {
            model.addAttribute("figure", figures.get(name));
            model.addAttribute("command", "edit");
            model.addAttribute("commandMsg", "Изменить");
            model.addAttribute("lines", lines.getAll());

            return OK(model, "admin/figures_edit");
        } catch (Exception ex) {
            attrs.addFlashAttribute("isError", true);
            attrs.addFlashAttribute("errorMsg", ex.getMessage());

            return Redirect("/admin/figures");
        }
    }

    @PostMapping("edit")
    public ModelAndView edit(Model model, @ModelAttribute("figure") @Valid Figure figure, BindingResult result, RedirectAttributes attrs) {
        if (result.hasErrors()) {
            return OK(model, "admin/figures_edit");
        }

        figures.addOrUpdate(figure);

        attrs.addFlashAttribute("isSuccess", true);
        attrs.addFlashAttribute("successMsg", "Фигура успешно изменена");

        return Redirect("/admin/figures");
    }

    @GetMapping("/admin/figures/remove/{name}")
    public ModelAndView remove(Model model, @PathVariable String name, RedirectAttributes attrs) {
        try {
            figures.remove(name);

            attrs.addFlashAttribute("isSuccess", true);
            attrs.addFlashAttribute("successMsg", "Фигура успешно удалена");
        } catch (Exception ex) {
            attrs.addFlashAttribute("isError", true);
            attrs.addFlashAttribute("errorMsg", ex.getMessage());
        }

        return Redirect("/admin/figures");
    }
}
