package chess.ai.controllers.expert;

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
@RequestMapping(path = "/expert/facts/**")
public class ExpertFactController extends BaseController {
    @Autowired
    private FactService facts;

    @GetMapping("/")
    public ModelAndView index(Model model) {
        model.addAttribute("facts", facts.getAll());

        return OK(model, "expert/facts");
    }

    @GetMapping("add")
    public ModelAndView addView(Model model) {
        model.addAttribute("fact", new Fact());
        model.addAttribute("command", "add");
        model.addAttribute("commandMsg", "Добавить");

        return OK(model, "expert/facts_edit");
    }

    @PostMapping("add")
    public ModelAndView add(Model model, @ModelAttribute("fact") @Valid Fact fact, BindingResult result, RedirectAttributes attrs) {
        if (result.hasErrors()) {
            model.addAttribute("command", "add");
            model.addAttribute("commandMsg", "Добавить");

            return OK(model, "expert/facts_edit");
        }


        if (facts.isSameExist(fact)) {
            attrs.addFlashAttribute("isError", true);
            attrs.addFlashAttribute("errorMsg", "Такой факт уже есть");

            return Redirect("add");
        }

        facts.addOrUpdateExpert(fact);

        attrs.addFlashAttribute("isSuccess", true);
        attrs.addFlashAttribute("successMsg", "Факт успешно добавлен");

        return Redirect("/expert/facts");
    }

    @GetMapping("/expert/facts/edit/{name}")
    public ModelAndView editView(Model model, @PathVariable String name, RedirectAttributes attrs) {
        try {
            final Fact fact = facts.get(name);
            if (!fact.getCanRemoved()) {
                attrs.addFlashAttribute("isError", true);
                attrs.addFlashAttribute("errorMsg", "Вы не можете изменить факт с name='" + name + "'");

                return Redirect("/expert/facts");
            }

            model.addAttribute("fact", fact);
            model.addAttribute("command", "edit");
            model.addAttribute("commandMsg", "Изменить");

            return OK(model, "expert/facts_edit");
        } catch (Exception ex) {
            attrs.addFlashAttribute("isError", true);
            attrs.addFlashAttribute("errorMsg", ex.getMessage());

            return Redirect("/expert/facts");
        }
    }

    @PostMapping("edit")
    public ModelAndView edit(Model model, @ModelAttribute("fact") @Valid Fact fact, BindingResult result, RedirectAttributes attrs) {
        if (result.hasErrors()) {
            model.addAttribute("command", "edit");
            model.addAttribute("commandMsg", "Изменить");

            return OK(model, "expert/facts_edit");
        }

        if (!fact.getCanRemoved()) {
            attrs.addFlashAttribute("isError", true);
            attrs.addFlashAttribute("errorMsg", "Вы не можете изменить факт с name='" + fact.getName() + "'");

            return Redirect("/expert/facts");
        }

        facts.addOrUpdateExpert(fact);

        attrs.addFlashAttribute("isSuccess", true);
        attrs.addFlashAttribute("successMsg", "Факт успешно изменён");

        return Redirect("/expert/facts");
    }

    @GetMapping("/expert/facts/remove/{name}")
    public ModelAndView remove(Model model, @PathVariable String name, RedirectAttributes attrs) {
        try {
            final Fact fact = facts.get(name);
            if (!fact.getCanRemoved()) {
                attrs.addFlashAttribute("isError", true);
                attrs.addFlashAttribute("errorMsg", "Вы не можете удалить факт с name='" + name + "'");

                return Redirect("/expert/facts");
            }


            facts.remove(fact);

            attrs.addFlashAttribute("isSuccess", true);
            attrs.addFlashAttribute("successMsg", "Факт успешно удалён");
        } catch (Exception ex) {
            attrs.addFlashAttribute("isError", true);
            attrs.addFlashAttribute("errorMsg", ex.getMessage());
        }

        return Redirect("/expert/facts");
    }
}
