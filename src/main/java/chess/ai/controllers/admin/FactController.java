package chess.ai.controllers.admin;

import chess.ai.controllers.BaseController;
import chess.ai.models.kb.Fact;
import chess.ai.services.kb.FactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/facts/**")
public class FactController extends BaseController {
    @Autowired
    private FactService facts;

    @GetMapping("/")
    public ModelAndView index(Model model) {
        model.addAttribute("facts", facts.getAll());

        return OK(model, "admin/facts");
    }

    @GetMapping("add")
    public ModelAndView addView(Model model) {
        model.addAttribute("fact", new Fact());
        model.addAttribute("command", "add");
        model.addAttribute("commandMsg", "Добавить");

        return OK(model, "admin/facts_edit");
    }

    @PostMapping("add")
    public ModelAndView add(Model model, @ModelAttribute("fact") @Valid Fact fact, BindingResult result, RedirectAttributes attrs) {
        if (result.hasErrors()) {
            model.addAttribute("command", "add");
            model.addAttribute("commandMsg", "Добавить");

            return OK(model, "admin/facts_edit");
        }


        if (facts.isSameExist(fact)) {
            attrs.addFlashAttribute("isError", true);
            attrs.addFlashAttribute("errorMsg", "Такой факт уже есть");

            return Redirect("add");
        }

        facts.addOrUpdateAdmin(fact);

        attrs.addFlashAttribute("isSuccess", true);
        attrs.addFlashAttribute("successMsg", "Факт успешно добавлен");

        return Redirect("/admin/facts");
    }

    @GetMapping("/admin/facts/edit/{name}")
    public ModelAndView editView(Model model, @PathVariable String name, RedirectAttributes attrs) {
        try {
            model.addAttribute("fact", facts.get(name));
            model.addAttribute("command", "edit");
            model.addAttribute("commandMsg", "Изменить");

            return OK(model, "admin/facts_edit");
        } catch (Exception ex) {
            attrs.addFlashAttribute("isError", true);
            attrs.addFlashAttribute("errorMsg", ex.getMessage());

            return Redirect("/admin/facts");
        }
    }

    @PostMapping("edit")
    public ModelAndView edit(Model model, @ModelAttribute("fact") @Valid Fact fact, BindingResult result, RedirectAttributes attrs) {
        if (result.hasErrors()) {
            model.addAttribute("command", "edit");
            model.addAttribute("commandMsg", "Изменить");

            return OK(model, "admin/facts_edit");
        }

        facts.addOrUpdateAdmin(fact);

        attrs.addFlashAttribute("isSuccess", true);
        attrs.addFlashAttribute("successMsg", "Факт успешно изменён");

        return Redirect("/admin/facts");
    }

    @GetMapping("/admin/facts/remove/{name}")
    public ModelAndView remove(Model model, @PathVariable String name, RedirectAttributes attrs) {
        try {
            facts.remove(name);

            attrs.addFlashAttribute("isSuccess", true);
            attrs.addFlashAttribute("successMsg", "Факт успешно удалён");
        } catch (Exception ex) {
            attrs.addFlashAttribute("isError", true);
            attrs.addFlashAttribute("errorMsg", ex.getMessage());
        }

        return Redirect("/admin/facts");
    }
}
