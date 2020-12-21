package chess.ai.controllers.admin;

import chess.ai.controllers.BaseController;
import chess.ai.models.db.Line;
import chess.ai.services.db.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping(path = "/admin/lines/**")
public class LineController extends BaseController {

    @Autowired
    private LineService lines;

    @GetMapping("/")
    public ModelAndView index(Model model) {
        model.addAttribute("lines", lines.getAll());

        return OK(model, "admin/lines");
    }

    @GetMapping("add")
    public ModelAndView addView(Model model) {
        model.addAttribute("line", new Line());
        model.addAttribute("command", "add");
        model.addAttribute("commandMsg", "Добавить");

        return OK(model, "admin/lines_edit");
    }

    @PostMapping("add")
    public ModelAndView add(Model model, @ModelAttribute("line") @Valid Line line, BindingResult result, RedirectAttributes attrs) {
        if (result.hasErrors()) {
            return OK(model, "admin/lines_edit");
        }

        if (lines.isSameExist(line)) {
            attrs.addFlashAttribute("isError", true);
            attrs.addFlashAttribute("errorMsg", "Такая линия уже есть");

            return Redirect("add");
        }

        lines.addOrUpdate(line);

        attrs.addFlashAttribute("isSuccess", true);
        attrs.addFlashAttribute("successMsg", "Линия успешно добавлена");

        return Redirect("/admin/lines");
    }

    @GetMapping("/admin/lines/edit/{id}")
    public ModelAndView editView(Model model, @PathVariable long id, RedirectAttributes attrs) {
        try {
            model.addAttribute("line", lines.get(id));
            model.addAttribute("command", "edit");
            model.addAttribute("commandMsg", "Изменить");

            return OK(model, "admin/lines_edit");
        } catch (Exception ex) {
            attrs.addFlashAttribute("isError", true);
            attrs.addFlashAttribute("errorMsg", ex.getMessage());

            return Redirect("/admin/lines");
        }
    }

    @PostMapping("edit")
    public ModelAndView edit(Model model, @ModelAttribute("line") @Valid Line line, BindingResult result, RedirectAttributes attrs) {
        if (result.hasErrors()) {
            return OK(model, "admin/lines_edit");
        }

        if (lines.isSameExistExcludeId(line, line.getId())) {
            attrs.addFlashAttribute("isError", true);
            attrs.addFlashAttribute("errorMsg", "Такая линия уже есть");

            return Redirect("add");
        }

        lines.addOrUpdate(line);

        attrs.addFlashAttribute("isSuccess", true);
        attrs.addFlashAttribute("successMsg", "Линия успешно изменена");

        return Redirect("/admin/lines");
    }

    @GetMapping("/admin/lines/remove/{id}")
    public ModelAndView remove(Model model, @PathVariable long id, RedirectAttributes attrs) {
        try {
            lines.remove(id);

            attrs.addFlashAttribute("isSuccess", true);
            attrs.addFlashAttribute("successMsg", "Линия успешно удалена");
        } catch (Exception ex) {
            attrs.addFlashAttribute("isError", true);
            attrs.addFlashAttribute("errorMsg", ex.getMessage());
        }

        return Redirect("/admin/lines");
    }
}
